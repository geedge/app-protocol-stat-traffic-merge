package com.zdjizhi.utils.functions.statistics;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.zdjizhi.common.pojo.Fields;
import com.zdjizhi.common.pojo.Metrics;
import com.zdjizhi.common.pojo.Tags;
import com.zdjizhi.utils.general.MetricUtil;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.functions.statistics
 * @Description:
 * @date 2023/4/2314:02
 */
public class DispersionCountWindow implements ReduceFunction<Tuple3<Tags, Fields, Long>> {
    private static final Log logger = LogFactory.get();

    @Override
    public Tuple3<Tags, Fields, Long> reduce(Tuple3<Tags, Fields, Long> value1, Tuple3<Tags, Fields, Long> value2) throws Exception {
        try {
            Fields cacheData = value1.f1;
            Fields newData = value2.f1;

            Fields metricsResult = MetricUtil.statisticsMetrics(cacheData, newData);

            return new Tuple3<>(value1.f0, metricsResult, value1.f2);
        } catch (RuntimeException e) {
            logger.error("An exception occurred during incremental aggregation! The message is:" + e.getMessage());
            return value1;
        }
    }


}
