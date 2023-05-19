package com.zdjizhi.utils.general;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.zdjizhi.common.pojo.Fields;
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
    public static Fields statisticsMetrics(Fields cacheData, Fields newData) {

        Long sessions = MetricUtil.longSum(cacheData.getSessions(), newData.getSessions());

        Long inBytes = MetricUtil.longSum(cacheData.getIn_bytes(), newData.getIn_bytes());
        Long outBytes = MetricUtil.longSum(cacheData.getOut_bytes(), newData.getOut_bytes());
        Long inPkts = MetricUtil.longSum(cacheData.getIn_pkts(), newData.getIn_pkts());
        Long outPkts = MetricUtil.longSum(cacheData.getOut_pkts(), newData.getOut_pkts());


        Long c2sBytes = MetricUtil.longSum(cacheData.getC2s_bytes(), newData.getC2s_bytes());
        Long s2cBytes = MetricUtil.longSum(cacheData.getS2c_bytes(), newData.getS2c_bytes());
        Long c2sPkts = MetricUtil.longSum(cacheData.getC2s_pkts(), newData.getC2s_pkts());
        Long s2cPkts = MetricUtil.longSum(cacheData.getS2c_pkts(), newData.getS2c_pkts());

        Long c2sFragments = MetricUtil.longSum(cacheData.getC2s_fragments(), newData.getC2s_fragments());
        Long s2cFragments = MetricUtil.longSum(cacheData.getS2c_fragments(), newData.getS2c_fragments());

        Long c2sTcpLostBytes = MetricUtil.longSum(cacheData.getC2s_tcp_lost_bytes(), newData.getC2s_tcp_lost_bytes());
        Long s2cTcpLostBytes = MetricUtil.longSum(cacheData.getS2c_tcp_lost_bytes(), newData.getS2c_tcp_lost_bytes());

        Long c2sTcpooorderPkts = MetricUtil.longSum(cacheData.getC2s_tcp_ooorder_pkts(), newData.getC2s_tcp_ooorder_pkts());
        Long s2cTcpooorderPkts = MetricUtil.longSum(cacheData.getS2c_tcp_ooorder_pkts(), newData.getS2c_tcp_ooorder_pkts());

        Long c2sTcpretransmittedPkts = MetricUtil.longSum(cacheData.getC2s_tcp_retransmitted_pkts(), newData.getC2s_tcp_retransmitted_pkts());
        Long s2cTcpretransmittedPkts = MetricUtil.longSum(cacheData.getS2c_tcp_retransmitted_pkts(), newData.getS2c_tcp_retransmitted_pkts());

        Long c2sTcpretransmittedBytes = MetricUtil.longSum(cacheData.getC2s_tcp_retransmitted_bytes(), newData.getC2s_tcp_retransmitted_bytes());
        Long s2cTcpretransmittedBytes = MetricUtil.longSum(cacheData.getS2c_tcp_retransmitted_bytes(), newData.getS2c_tcp_retransmitted_bytes());

        String clientIpSketch = MetricUtil.hllSketchUnion(cacheData.getClient_ip_sketch(), newData.getClient_ip_sketch());

        return new Fields(sessions,
                inBytes, outBytes, inPkts, outPkts,
                c2sPkts, s2cPkts, c2sBytes, s2cBytes,
                c2sFragments, s2cFragments,
                c2sTcpLostBytes, s2cTcpLostBytes,
                c2sTcpooorderPkts, s2cTcpooorderPkts,
                c2sTcpretransmittedPkts, s2cTcpretransmittedPkts,
                c2sTcpretransmittedBytes, s2cTcpretransmittedBytes,
                clientIpSketch);
    }

    /**
     * Long类型的数据求和
     *
     * @param value1 第一个值
     * @param value2 第二个值
     * @return value1 + value2
     */
    private static Long longSum(Long value1, Long value2) {
        Long result;
        try {
            if (value1 >= 0 && value2 >= 0) {
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
