package com.zdjizhi.utils.functions.statistics;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.zdjizhi.common.config.GlobalConfig;
import com.zdjizhi.common.pojo.Fields;
import com.zdjizhi.common.pojo.Metrics;
import com.zdjizhi.common.pojo.Tags;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.functions.statistics
 * @Description:
 * @date 2023/4/2314:43
 */
public class MergeCountWindow extends ProcessWindowFunction<Tuple3<Tags, Fields,Long>, Metrics, String, TimeWindow> {
    private static final Log logger = LogFactory.get();

    @Override
    public void process(String windowKey, Context context, Iterable<Tuple3<Tags, Fields,Long>> input, Collector<Metrics> output) throws Exception {
        try {
            long timestamp_ms = context.window().getStart();
            for (Tuple3<Tags, Fields,Long> tuple : input) {
                Tags tags = tuple.f0;
                Fields fields = tuple.f1;
                Metrics metrics = new Metrics(GlobalConfig.MEASUREMENT_NAME, tags, fields, timestamp_ms);
                output.collect(metrics);
            }

        } catch (RuntimeException e) {
            logger.error("An exception occurred in the process of full data aggregation! The message is:" + e.getMessage());
        }
    }

}
