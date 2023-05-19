package com.zdjizhi.utils.functions.statistics;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.zdjizhi.common.pojo.Fields;
import com.zdjizhi.common.pojo.Tags;
import com.zdjizhi.utils.general.MetricUtil;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.functions.statistics
 * @Description:
 * @date 2023/4/2314:02
 */
public class DispersionCountWindow implements ReduceFunction<Tuple2<Tags, Fields>> {
    private static final Log logger = LogFactory.get();

    @Override
    public Tuple2<Tags, Fields> reduce(Tuple2<Tags, Fields> value1, Tuple2<Tags, Fields> value2) throws Exception {
        try {
            Fields cacheData = value1.f1;
            Fields newData = value2.f1;

            Fields metricsResult = MetricUtil.statisticsMetrics(cacheData, newData);

            return new Tuple2<>(value1.f0, metricsResult);
        } catch (RuntimeException e) {
            logger.error("An exception occurred during incremental aggregation! The message is:" + e.getMessage());
            return value1;
        }
    }
}
