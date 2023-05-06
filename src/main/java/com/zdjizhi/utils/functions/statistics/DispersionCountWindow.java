package com.zdjizhi.utils.functions.statistics;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.zdjizhi.common.pojo.AppProtocol;
import com.zdjizhi.utils.general.MetricUtil;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.functions.statistics
 * @Description:
 * @date 2023/4/2314:02
 */
public class DispersionCountWindow implements ReduceFunction<Tuple2<String, AppProtocol>> {
    private static final Log logger = LogFactory.get();

    @Override
    public Tuple2<String, AppProtocol> reduce(Tuple2<String, AppProtocol> value1, Tuple2<String, AppProtocol> value2) throws Exception {
        try {
            AppProtocol cacheData = value1.f1;
            AppProtocol newData = value2.f1;

            MetricUtil.statisticsMetrics(cacheData, newData);

            return new Tuple2<>(value1.f0, cacheData);
        } catch (RuntimeException e) {
            logger.error("An exception occurred during incremental aggregation! The message is:" + e.getMessage());
            return value1;
        }
    }
}
