package com.zdjizhi.utils.functions.statistics;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.zdjizhi.common.pojo.AppProtocol;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.functions.statistics
 * @Description:
 * @date 2023/4/2314:43
 */
public class MergeCountWindow extends ProcessWindowFunction<Tuple2<String, AppProtocol>, AppProtocol, String, TimeWindow> {
    private static final Log logger = LogFactory.get();

    @Override
    public void process(String windowKey, Context context, Iterable<Tuple2<String, AppProtocol>> input, Collector<AppProtocol> output) throws Exception {
        try {
            Long endTime = context.window().getEnd() / 1000;
            for (Tuple2<String, AppProtocol> tuple : input) {
                AppProtocol data = tuple.f1;
                data.setTimestamp(endTime);
                output.collect(data);
            }
        } catch (RuntimeException e) {
            logger.error("An exception occurred in the process of full data aggregation! The message is:" + e.getMessage());
        }
    }

}
