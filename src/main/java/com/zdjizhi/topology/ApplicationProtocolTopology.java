package com.zdjizhi.topology;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.zdjizhi.common.config.MergeConfigs;
import com.zdjizhi.common.config.MergeConfiguration;
import com.zdjizhi.common.pojo.Fields;
import com.zdjizhi.common.pojo.Metrics;
import com.zdjizhi.common.pojo.Tags;
import com.zdjizhi.utils.functions.keyby.DimensionKeyBy;
import com.zdjizhi.utils.functions.map.ResultFlatMap;
import com.zdjizhi.utils.functions.process.ParsingData;
import com.zdjizhi.utils.functions.statistics.DispersionCountWindow;
import com.zdjizhi.utils.functions.statistics.MergeCountWindow;
import com.zdjizhi.utils.kafka.KafkaConsumer;
import com.zdjizhi.utils.kafka.KafkaProducer;
import org.apache.flink.api.common.eventtime.*;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.time.Duration;

import static com.zdjizhi.common.config.MergeConfigs.*;

/**
 * @author qidaijie
 * @Package com.zdjizhi.topology
 * @Description:
 * @date 2021/5/2016:42
 */
public class ApplicationProtocolTopology {
    private static final Log logger = LogFactory.get();

    public static void main(String[] args) {
        try {

            // param check
            if (args.length < 1) {
                throw new IllegalArgumentException("Error: Not found properties path. " +
                        "\nUsage: flink -c xxx xxx.jar app.properties.");
            }

            final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

            ParameterTool tool = ParameterTool.fromPropertiesFile(args[0]);
            final Configuration config = tool.getConfiguration();
            environment.getConfig().setGlobalJobParameters(config);
            final MergeConfiguration fusionConfiguration = new MergeConfiguration(config);


            //水印
            WatermarkStrategy<Tuple3<Tags, Fields, Long>> strategyForSession = WatermarkStrategy
                    .<Tuple3<Tags, Fields, Long>>forBoundedOutOfOrderness(Duration.ofSeconds(config.get(WARTERMARK_MAX_ORDERNESS)))
                    .withTimestampAssigner((element, timestamp) -> element.f2);

            //数据源
            DataStream<String> streamSource = environment.addSource(
                    KafkaConsumer.getKafkaConsumer(fusionConfiguration.getProperties(SOURCE_KAFKA_PROPERTIES_PREFIX),
                            config.get(SOURCE_KAFKA_TOPIC),
                            config.get(STARTUP_MODE)));

            //解析数据
            SingleOutputStreamOperator<Tuple3<Tags, Fields, Long>> parseDataProcess = streamSource.process(new ParsingData())
                    .assignTimestampsAndWatermarks(strategyForSession)
                    .name("ParseDataProcess");

            //增量聚合窗口
            SingleOutputStreamOperator<Metrics> dispersionCountWindow = parseDataProcess.keyBy(new DimensionKeyBy())
                    .window(TumblingEventTimeWindows.of(Time.seconds(config.get(MergeConfigs.COUNT_WINDOW_TIME))))
                    .reduce(new DispersionCountWindow(), new MergeCountWindow())
                    .name("DispersionCountWindow");

            //拆分数据
            SingleOutputStreamOperator<String> resultFlatMap = dispersionCountWindow.flatMap(new ResultFlatMap())
                    .name("ResultFlatMap");

            //输出
            resultFlatMap.addSink(KafkaProducer.getKafkaProducer(fusionConfiguration.getProperties(SINK_KAFKA_PROPERTIES_PREFIX),
                    config.get(SINK_KAFKA_TOPIC),
                    config.get(LOG_FAILURES_ONLY)));

            environment.execute("APP-PROTOCOL-STAT-TRAFFIC-MERGE");
        } catch (Exception e) {
            logger.error("This Flink task start ERROR! Exception information is :");
            e.printStackTrace();
        }

    }

}
