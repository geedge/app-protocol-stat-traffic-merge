package com.zdjizhi.utils.general;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.zdjizhi.common.pojo.AppProtocol;
import com.zdjizhi.utils.StringUtil;
import org.apache.datasketches.hll.HllSketch;
import org.apache.datasketches.hll.Union;

import java.util.Base64;


/**
 * @author qidaijie
 * @Package com.zdjizhi.storm.utils.general
 * @Description:
 * @date 2021/7/2015:31
 */
public class MetricUtil {
    private static final Log logger = LogFactory.get();


    /**
     * 用于对业务指标进行统计
     *
     * @param cacheData 缓存中数据
     * @param newData   新数据
     */
    public static void statisticsMetrics(AppProtocol cacheData, AppProtocol newData) {
        cacheData.setSessions(MetricUtil.longSum(cacheData.getSessions(), newData.getSessions()));

        cacheData.setIn_bytes(MetricUtil.longSum(cacheData.getIn_bytes(), newData.getIn_bytes()));
        cacheData.setOut_pkts(MetricUtil.longSum(cacheData.getOut_bytes(), newData.getOut_bytes()));
        cacheData.setIn_pkts(MetricUtil.longSum(cacheData.getIn_pkts(), newData.getIn_pkts()));
        cacheData.setOut_pkts(MetricUtil.longSum(cacheData.getOut_pkts(), newData.getOut_pkts()));

        cacheData.setC2s_bytes(MetricUtil.longSum(cacheData.getC2s_bytes(), newData.getC2s_bytes()));
        cacheData.setS2c_bytes(MetricUtil.longSum(cacheData.getS2c_bytes(), newData.getS2c_bytes()));
        cacheData.setC2s_pkts(MetricUtil.longSum(cacheData.getC2s_pkts(), newData.getC2s_pkts()));
        cacheData.setS2c_pkts(MetricUtil.longSum(cacheData.getS2c_pkts(), newData.getS2c_pkts()));

        cacheData.setC2s_fragments(MetricUtil.longSum(cacheData.getC2s_fragments(), newData.getC2s_fragments()));
        cacheData.setS2c_fragments(MetricUtil.longSum(cacheData.getS2c_fragments(), newData.getS2c_fragments()));

        cacheData.setC2s_tcp_lost_bytes(MetricUtil.longSum(cacheData.getC2s_tcp_lost_bytes(), newData.getC2s_tcp_lost_bytes()));
        cacheData.setS2c_tcp_lost_bytes(MetricUtil.longSum(cacheData.getS2c_tcp_lost_bytes(), newData.getS2c_tcp_lost_bytes()));

        cacheData.setC2s_tcp_ooorder_pkts(MetricUtil.longSum(cacheData.getC2s_tcp_ooorder_pkts(), newData.getC2s_tcp_ooorder_pkts()));
        cacheData.setS2c_tcp_ooorder_pkts(MetricUtil.longSum(cacheData.getS2c_tcp_ooorder_pkts(), newData.getS2c_tcp_ooorder_pkts()));

        cacheData.setC2s_tcp_retransmitted_bytes(MetricUtil.longSum(cacheData.getC2s_tcp_retransmitted_bytes(), newData.getC2s_tcp_retransmitted_bytes()));
        cacheData.setS2c_tcp_retransmitted_bytes(MetricUtil.longSum(cacheData.getS2c_tcp_retransmitted_bytes(), newData.getS2c_tcp_retransmitted_bytes()));

        cacheData.setC2s_tcp_retransmitted_pkts(MetricUtil.longSum(cacheData.getC2s_tcp_retransmitted_pkts(), newData.getC2s_tcp_retransmitted_pkts()));
        cacheData.setS2c_tcp_retransmitted_pkts(MetricUtil.longSum(cacheData.getS2c_tcp_retransmitted_pkts(), newData.getS2c_tcp_retransmitted_pkts()));

        cacheData.setClient_ip_sketch(MetricUtil.hllSketchUnion(cacheData.getClient_ip_sketch(), newData.getClient_ip_sketch()));
    }

    /**
     * Long类型的数据求和
     *
     * @param value1 第一个值
     * @param value2 第二个值
     * @return value1 + value2
     */
    private static Long longSum(Long value1, Long value2) {
        Long result = 0L;
        try {
            if (value1 > 0 && value2 > 0) {
                result = value1 + value2;
            } else {
                result = value1;
            }
        } catch (RuntimeException e) {
            logger.error("Abnormal sending of traffic indicator statistics! The message is:" + e.getMessage());
            result = value1;
        }
        return result;

    }

    /**
     * @param cacheHll 缓存的sketch
     * @param newHll   聚合后的sketch
     * @return 合并后的sketch
     */
    private static String hllSketchUnion(String cacheHll, String newHll) {
        Union union = new Union(12);
        try {
            if (StringUtil.isNotBlank(cacheHll)) {
                byte[] cacheHllBytes = Base64.getDecoder().decode(cacheHll);
                HllSketch cacheSketch = HllSketch.heapify(cacheHllBytes);
                union.update(cacheSketch);
            }

            if (StringUtil.isNotBlank(newHll)) {
                byte[] newHllBytes = Base64.getDecoder().decode(newHll);
                HllSketch newSketch = HllSketch.heapify(newHllBytes);
                union.update(newSketch);
            }
            return Base64.getEncoder().encodeToString(union.getResult().toUpdatableByteArray());

        } catch (RuntimeException e) {
            logger.error("Merge hllSketch results abnormal! The message is:" + e.getMessage());
            return null;
        }
    }

}
