package com.zdjizhi.topology;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.zdjizhi.common.config.GlobalConfig;
import com.zdjizhi.common.pojo.Fields;
import com.zdjizhi.common.pojo.Metrics;
import com.zdjizhi.common.pojo.Tags;
import com.zdjizhi.utils.functions.filter.DataTypeFilter;
import com.zdjizhi.utils.functions.keyby.DimensionKeyBy;
import com.zdjizhi.utils.functions.map.MetricsParseMap;
import com.zdjizhi.utils.functions.map.ResultFlatMap;
import com.zdjizhi.utils.functions.statistics.DispersionCountWindow;
import com.zdjizhi.utils.functions.statistics.MergeCountWindow;
import com.zdjizhi.utils.kafka.KafkaConsumer;
import com.zdjizhi.utils.kafka.KafkaProducer;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;


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
            final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

            //解析原始日志
            DataStream<String> streamSource = environment.addSource(KafkaConsumer.getKafkaConsumer())
                    .setParallelism(GlobalConfig.SOURCE_PARALLELISM).name(GlobalConfig.SOURCE_KAFKA_TOPIC);

            SingleOutputStreamOperator<String> appProtocolFilter = streamSource.filter(new DataTypeFilter())
                    .name("appProtocolFilter").setParallelism(GlobalConfig.SOURCE_PARALLELISM);


            SingleOutputStreamOperator<Tuple2<Tags, Fields>> parseDataMap = appProtocolFilter.map(new MetricsParseMap())
                    .name("ParseDataMap").setParallelism(GlobalConfig.PARSE_PARALLELISM);

            SingleOutputStreamOperator<Metrics> dispersionCountWindow = parseDataMap.keyBy(new DimensionKeyBy())
                    .window(TumblingProcessingTimeWindows.of(Time.seconds(GlobalConfig.COUNT_WINDOW_TIME)))
                    .reduce(new DispersionCountWindow(), new MergeCountWindow())
                    .name("DispersionCountWindow")
                    .setParallelism(GlobalConfig.WINDOW_PARALLELISM);

            SingleOutputStreamOperator<String> resultFlatMap = dispersionCountWindow.flatMap(new ResultFlatMap())
                    .name("ResultFlatMap").setParallelism(GlobalConfig.SINK_PARALLELISM);


            resultFlatMap.addSink(KafkaProducer.getKafkaProducer())
                    .setParallelism(GlobalConfig.SINK_PARALLELISM).name(GlobalConfig.SINK_KAFKA_TOPIC);

            environment.execute(args[0]);
        } catch (Exception e) {
            logger.error("This Flink task start ERROR! Exception information is :" + e);
        }

    }

}
