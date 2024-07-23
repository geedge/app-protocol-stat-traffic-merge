package com.zdjizhi.topology;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONPath;
import com.alibaba.fastjson2.JSONReader;
import com.zdjizhi.common.config.MergeConfigs;
import com.zdjizhi.common.config.MergeConfiguration;
import com.zdjizhi.common.pojo.*;
import com.zdjizhi.utils.kafka.KafkaConsumer;
import com.zdjizhi.utils.kafka.KafkaProducer;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.eventtime.*;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple6;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.apache.flink.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;

import static com.zdjizhi.common.config.MergeConfigs.*;

/**
 * @author lifengchao
 * @Package com.zdjizhi.topology
 * @Description:
 * @date 2024/7/23 11:20
 */
public class ApplicationProtocolTopology {
    static final Logger LOG = LoggerFactory.getLogger(ApplicationProtocolTopology.class);

    public static void main(String[] args) throws Exception{
        // param check
        if (args.length < 1) {
            throw new IllegalArgumentException("Error: Not found properties path. " +
                    "\nUsage: flink -c xxx xxx.jar app.properties.");
        }

        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.setParallelism(1);

        ParameterTool tool = ParameterTool.fromPropertiesFile(args[0]);
        final Configuration config = tool.getConfiguration();
        environment.getConfig().setGlobalJobParameters(config);
        final MergeConfiguration fusionConfiguration = new MergeConfiguration(config);

        //水印
        WatermarkStrategy<Data> strategyForSession = WatermarkStrategy
                .<Data>forBoundedOutOfOrderness(Duration.ofSeconds(config.get(WARTERMARK_MAX_ORDERNESS)))
                .withTimestampAssigner((element, timestamp) -> element.getTimestamp_ms());

        //数据源
        DataStream<String> streamSource = environment.addSource(
                KafkaConsumer.getKafkaConsumer(fusionConfiguration.getProperties(SOURCE_KAFKA_PROPERTIES_PREFIX),
                        config.get(SOURCE_KAFKA_TOPIC),
                        config.get(STARTUP_MODE)));

        //解析数据
        SingleOutputStreamOperator<Data> dataStream = streamSource.flatMap(parseFlatMapFunction()).assignTimestampsAndWatermarks(strategyForSession);

        //聚合 + 拆分
        SingleOutputStreamOperator<String> rstStream = dataStream.keyBy(keySelector())
                .window(TumblingEventTimeWindows.of(Time.seconds(config.get(COUNT_WINDOW_TIME))))
                .aggregate(aggregateFunction(), processWindowFunction());

        //输出
        rstStream.addSink(KafkaProducer.getKafkaProducer(fusionConfiguration.getProperties(SINK_KAFKA_PROPERTIES_PREFIX), config.get(SINK_KAFKA_TOPIC), config.get(LOG_FAILURES_ONLY)));

        environment.execute(config.get(JOB_NAME));
    }

    private static KeySelector<Data, Tuple6<Integer, String, String, String, String, String>> keySelector(){
        return new KeySelector<Data, Tuple6<Integer, String, String, String, String, String>>() {
            @Override
            public Tuple6<Integer, String, String, String, String, String> getKey(Data data) throws Exception {
                return new Tuple6<>(data.vsys_id, data.device_id, data.device_group, data.data_center, data.decoded_path, data.app);
            }
        };
    }

    private static FlatMapFunction<String, Data> parseFlatMapFunction(){
        return new RichFlatMapFunction<String, Data>() {
            private JSONPath namePath;

            @Override
            public void open(Configuration parameters) throws Exception {
                super.open(parameters);
                namePath = JSONPath.of("$.name");
            }

            @Override
            public void flatMap(String value, Collector<Data> out) throws Exception {
                JSONReader parser = JSONReader.of(value);
                try {
                    Object name = namePath.extract(parser);
                    if(!"traffic_application_protocol_stat".equals(name)){
                        return;
                    }

                    Data data = JSON.parseObject(value, Data.class);
                    String decodedPath = data.getDecoded_path();
                    if(StringUtils.isBlank(decodedPath)){
                        return;
                    }

                    String appFullPath = data.getApp();
                    if(StringUtils.isBlank(appFullPath)){
                        out.collect(data);
                        return;
                    }
                    // decoded_path和app进行拼接，格式化
                    String[] appSplits = appFullPath.split("\\.");
                    data.setApp(appSplits[appSplits.length -1]);
                    String firstAppProtocol = appSplits[0];
                    String endProtocol = decodedPath.substring(decodedPath.lastIndexOf(".") + 1);
                    if (endProtocol.equals(firstAppProtocol)) {
                        if(appSplits.length > 1){
                            decodedPath = decodedPath + appFullPath.substring(appFullPath.indexOf("."));
                            data.setDecoded_path(decodedPath);
                        }
                    }else{
                        decodedPath = decodedPath + "." + appFullPath;
                        data.setDecoded_path(decodedPath);
                    }
                    out.collect(data);
                } catch (Exception e) {
                    LOG.error("parse error for value:" + value, e);
                } finally {
                    parser.close();
                }
            }
        };
    }

    private static AggregateFunction<Data, ResultData, ResultData> aggregateFunction(){
        return new AggregateFunction<Data, ResultData, ResultData>() {

            @Override
            public ResultData createAccumulator() {
                return new ResultData();
            }

            @Override
            public ResultData add(Data value, ResultData acc) {
                acc.sessions = acc.sessions + value.sessions;
                acc.in_bytes = acc.in_bytes + value.in_bytes;
                acc.out_bytes = acc.out_bytes + value.out_bytes;
                acc.in_pkts = acc.in_pkts + value.in_pkts;
                acc.out_pkts = acc.out_pkts + value.out_pkts;
                acc.c2s_pkts = acc.c2s_pkts + value.c2s_pkts;
                acc.s2c_pkts = acc.s2c_pkts + value.s2c_pkts;
                acc.c2s_bytes = acc.c2s_bytes + value.c2s_bytes;
                acc.s2c_bytes = acc.s2c_bytes + value.s2c_bytes;
                acc.c2s_fragments = acc.c2s_fragments + value.c2s_fragments;
                acc.s2c_fragments = acc.s2c_fragments + value.s2c_fragments;
                acc.c2s_tcp_lost_bytes = acc.c2s_tcp_lost_bytes + value.c2s_tcp_lost_bytes;
                acc.s2c_tcp_lost_bytes = acc.s2c_tcp_lost_bytes + value.s2c_tcp_lost_bytes;
                acc.c2s_tcp_ooorder_pkts = acc.c2s_tcp_ooorder_pkts + value.c2s_tcp_ooorder_pkts;
                acc.s2c_tcp_ooorder_pkts = acc.s2c_tcp_ooorder_pkts + value.s2c_tcp_ooorder_pkts;
                acc.c2s_tcp_retransmitted_pkts = acc.c2s_tcp_retransmitted_pkts + value.c2s_tcp_retransmitted_pkts;
                acc.s2c_tcp_retransmitted_pkts = acc.s2c_tcp_retransmitted_pkts + value.s2c_tcp_retransmitted_pkts;
                acc.c2s_tcp_retransmitted_bytes = acc.c2s_tcp_retransmitted_bytes + value.c2s_tcp_retransmitted_bytes;
                acc.s2c_tcp_retransmitted_bytes = acc.s2c_tcp_retransmitted_bytes + value.s2c_tcp_retransmitted_bytes;
                return acc;
            }

            @Override
            public ResultData getResult(ResultData acc) {
                return acc;
            }

            @Override
            public ResultData merge(ResultData a, ResultData b) {
                a.sessions = a.sessions + b.sessions;
                a.in_bytes = a.in_bytes + b.in_bytes;
                a.out_bytes = a.out_bytes + b.out_bytes;
                a.in_pkts = a.in_pkts + b.in_pkts;
                a.out_pkts = a.out_pkts + b.out_pkts;
                a.c2s_pkts = a.c2s_pkts + b.c2s_pkts;
                a.s2c_pkts = a.s2c_pkts + b.s2c_pkts;
                a.c2s_bytes = a.c2s_bytes + b.c2s_bytes;
                a.s2c_bytes = a.s2c_bytes + b.s2c_bytes;
                a.c2s_fragments = a.c2s_fragments + b.c2s_fragments;
                a.s2c_fragments = a.s2c_fragments + b.s2c_fragments;
                a.c2s_tcp_lost_bytes = a.c2s_tcp_lost_bytes + b.c2s_tcp_lost_bytes;
                a.s2c_tcp_lost_bytes = a.s2c_tcp_lost_bytes + b.s2c_tcp_lost_bytes;
                a.c2s_tcp_ooorder_pkts = a.c2s_tcp_ooorder_pkts + b.c2s_tcp_ooorder_pkts;
                a.s2c_tcp_ooorder_pkts = a.s2c_tcp_ooorder_pkts + b.s2c_tcp_ooorder_pkts;
                a.c2s_tcp_retransmitted_pkts = a.c2s_tcp_retransmitted_pkts + b.c2s_tcp_retransmitted_pkts;
                a.s2c_tcp_retransmitted_pkts = a.s2c_tcp_retransmitted_pkts + b.s2c_tcp_retransmitted_pkts;
                a.c2s_tcp_retransmitted_bytes = a.c2s_tcp_retransmitted_bytes + b.c2s_tcp_retransmitted_bytes;
                a.s2c_tcp_retransmitted_bytes = a.s2c_tcp_retransmitted_bytes + b.s2c_tcp_retransmitted_bytes;
                return a;
            }
        };
    }

    private static ProcessWindowFunction<ResultData, String, Tuple6<Integer, String, String, String, String, String>, TimeWindow> processWindowFunction(){
        return new ProcessWindowFunction<ResultData, String, Tuple6<Integer, String, String, String, String, String>, TimeWindow>() {
            private String NAME = null;

            @Override
            public void open(Configuration parameters) throws Exception {
                super.open(parameters);
                final Configuration configuration = (Configuration) getRuntimeContext().getExecutionConfig().getGlobalJobParameters();
                NAME = configuration.get(MergeConfigs.MEASUREMENT_NAME);
                Preconditions.checkArgument(StringUtils.isNotBlank(NAME));
            }

            @Override
            public void process(Tuple6<Integer, String, String, String, String, String> key, ProcessWindowFunction<ResultData, String, Tuple6<Integer, String, String, String, String, String>, TimeWindow>.Context context, Iterable<ResultData> elements, Collector<String> out) throws Exception {
                long timestamp_ms = context.window().getStart();
                for (ResultData ele : elements) {
                    ele.timestamp_ms = timestamp_ms;
                    ele.name = NAME;
                    ele.vsys_id = key.f0;
                    ele.device_id = key.f1;
                    ele.device_group = key.f2;
                    ele.data_center = key.f3;
                    ele.app_name = null;
                    String decodedPath = key.f4;
                    String app = key.f5;

                    // 拆分
                    int index = decodedPath.indexOf('.');
                    String subDecodedPath;
                    while (index > 0) {
                        subDecodedPath = decodedPath.substring(0, index);
                        ele.protocol_stack_id = subDecodedPath;
                        out.collect(JSON.toJSONString(ele));
                        index = decodedPath.indexOf('.', index + 1);
                    }

                    ele.app_name = app;
                    ele.protocol_stack_id = decodedPath;
                    out.collect(JSON.toJSONString(ele));
                }
            }
        };
    }

    private static DataStream<String> testSource(StreamExecutionEnvironment environment){
        return environment.fromCollection(Arrays.asList(
                "{\"name\":\"traffic_application_protocol_stat\",\"decoded_path\":\"ETHERNET.IPv4.TCP.ssh\",\"app\":\"ssh\",\"device_id\":\"9800165603191151\",\"device_group\":\"group-xxg-tsgx\",\"data_center\":\"center-xxg-tsgx\",\"vsys_id\":1,\"in_bytes\":720,\"out_bytes\":1200,\"in_pkts\":8,\"out_pkts\":16,\"c2s_pkts\":16,\"s2c_pkts\":8,\"c2s_bytes\":1200,\"s2c_bytes\":720,\"timestamp_ms\":1719990000033}",
                "{\"name\":\"traffic_application_protocol_stat\",\"decoded_path\":\"ETHERNET.IPv4.TCP.http\",\"app\":\"Gary-ApplicationTest\",\"device_id\":\"9800165603191151\",\"device_group\":\"group-xxg-tsgx\",\"data_center\":\"center-xxg-tsgx\",\"vsys_id\":1,\"in_bytes\":2536,\"out_bytes\":2237,\"in_pkts\":8,\"out_pkts\":7,\"c2s_pkts\":7,\"s2c_pkts\":8,\"c2s_bytes\":2237,\"s2c_bytes\":2536,\"c2s_tcp_retransmitted_pkts\":2,\"c2s_tcp_retransmitted_bytes\":120,\"timestamp_ms\":1719990000033}",
                "{\"name\":\"traffic_application_protocol_stat\",\"decoded_path\":\"ETHERNET.IPv4.TCP.http\",\"app\":\"ms_edge\",\"device_id\":\"9800165603191151\",\"device_group\":\"group-xxg-tsgx\",\"data_center\":\"center-xxg-tsgx\",\"vsys_id\":1,\"in_bytes\":326282,\"out_bytes\":8322,\"in_pkts\":241,\"out_pkts\":125,\"c2s_pkts\":125,\"s2c_pkts\":241,\"c2s_bytes\":8322,\"s2c_bytes\":326282,\"s2c_tcp_ooorder_pkts\":1,\"timestamp_ms\":1719990000033}",
                "{\"name\":\"traffic_application_protocol_stat\",\"decoded_path\":\"ETHERNET.IPv4.TCP\",\"app\":\"port_443\",\"device_id\":\"9800165603191151\",\"device_group\":\"group-xxg-tsgx\",\"data_center\":\"center-xxg-tsgx\",\"vsys_id\":1,\"in_bytes\":66,\"in_pkts\":1,\"s2c_pkts\":1,\"s2c_bytes\":66,\"timestamp_ms\":1719990000033}",
                "{\"name\":\"traffic_application_protocol_stat\",\"decoded_path\":\"ETHERNET.IPv4.TCP.ssl\",\"app\":\"port_443\",\"device_id\":\"9800165603191151\",\"device_group\":\"group-xxg-tsgx\",\"data_center\":\"center-xxg-tsgx\",\"vsys_id\":1,\"in_bytes\":66,\"out_bytes\":60,\"in_pkts\":1,\"out_pkts\":1,\"c2s_pkts\":1,\"s2c_pkts\":1,\"c2s_bytes\":60,\"s2c_bytes\":66,\"c2s_tcp_retransmitted_pkts\":1,\"c2s_tcp_retransmitted_bytes\":60,\"timestamp_ms\":1719990000033}",
                "{\"name\":\"traffic_application_protocol_stat\",\"decoded_path\":\"ETHERNET.IPv4.TCP.ssl\",\"app\":\"ssl.port_444\",\"device_id\":\"9800165603191151\",\"device_group\":\"group-xxg-tsgx\",\"data_center\":\"center-xxg-tsgx\",\"vsys_id\":1,\"in_bytes\":66,\"out_bytes\":60,\"in_pkts\":1,\"out_pkts\":1,\"c2s_pkts\":1,\"s2c_pkts\":1,\"c2s_bytes\":60,\"s2c_bytes\":66,\"c2s_tcp_retransmitted_pkts\":1,\"c2s_tcp_retransmitted_bytes\":60,\"timestamp_ms\":1719990000033}",
                "{}"
        ));
    }
}
