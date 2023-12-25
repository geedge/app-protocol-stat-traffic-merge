package com.zdjizhi.utils.general;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.zdjizhi.common.pojo.Fields;


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

        long sessions = MetricUtil.longSum(cacheData.getSessions(), newData.getSessions());

        long inBytes = MetricUtil.longSum(cacheData.getIn_bytes(), newData.getIn_bytes());
        long outBytes = MetricUtil.longSum(cacheData.getOut_bytes(), newData.getOut_bytes());
        long inPkts = MetricUtil.longSum(cacheData.getIn_pkts(), newData.getIn_pkts());
        long outPkts = MetricUtil.longSum(cacheData.getOut_pkts(), newData.getOut_pkts());


        long c2sBytes = MetricUtil.longSum(cacheData.getC2s_bytes(), newData.getC2s_bytes());
        long s2cBytes = MetricUtil.longSum(cacheData.getS2c_bytes(), newData.getS2c_bytes());
        long c2sPkts = MetricUtil.longSum(cacheData.getC2s_pkts(), newData.getC2s_pkts());
        long s2cPkts = MetricUtil.longSum(cacheData.getS2c_pkts(), newData.getS2c_pkts());

        long c2sFragments = MetricUtil.longSum(cacheData.getC2s_fragments(), newData.getC2s_fragments());
        long s2cFragments = MetricUtil.longSum(cacheData.getS2c_fragments(), newData.getS2c_fragments());

        long c2sTcpLostBytes = MetricUtil.longSum(cacheData.getC2s_tcp_lost_bytes(), newData.getC2s_tcp_lost_bytes());
        long s2cTcpLostBytes = MetricUtil.longSum(cacheData.getS2c_tcp_lost_bytes(), newData.getS2c_tcp_lost_bytes());

        long c2sTcpooorderPkts = MetricUtil.longSum(cacheData.getC2s_tcp_ooorder_pkts(), newData.getC2s_tcp_ooorder_pkts());
        long s2cTcpooorderPkts = MetricUtil.longSum(cacheData.getS2c_tcp_ooorder_pkts(), newData.getS2c_tcp_ooorder_pkts());

        long c2sTcpretransmittedPkts = MetricUtil.longSum(cacheData.getC2s_tcp_retransmitted_pkts(), newData.getC2s_tcp_retransmitted_pkts());
        long s2cTcpretransmittedPkts = MetricUtil.longSum(cacheData.getS2c_tcp_retransmitted_pkts(), newData.getS2c_tcp_retransmitted_pkts());

        long c2sTcpretransmittedBytes = MetricUtil.longSum(cacheData.getC2s_tcp_retransmitted_bytes(), newData.getC2s_tcp_retransmitted_bytes());
        long s2cTcpretransmittedBytes = MetricUtil.longSum(cacheData.getS2c_tcp_retransmitted_bytes(), newData.getS2c_tcp_retransmitted_bytes());

        return new Fields(sessions,
                inBytes, outBytes, inPkts, outPkts,
                c2sPkts, s2cPkts, c2sBytes, s2cBytes,
                c2sFragments, s2cFragments,
                c2sTcpLostBytes, s2cTcpLostBytes,
                c2sTcpooorderPkts, s2cTcpooorderPkts,
                c2sTcpretransmittedPkts, s2cTcpretransmittedPkts,
                c2sTcpretransmittedBytes, s2cTcpretransmittedBytes);
    }

    /**
     * Long类型的数据求和
     *
     * @param cacheData 缓存中的值
     * @param newData   新来数据的值
     * @return cacheData + newData
     */
    private static long longSum(long cacheData, long newData) {

        long result;
        try {
            if (cacheData >= 0 && newData >= 0) {
                result = cacheData + newData;
            } else {
                result = cacheData;
            }
        } catch (RuntimeException e) {
            logger.error("Abnormal sending of traffic indicator statistics! The message is:{}", e);
            result = cacheData;
        }
        return result;

    }

}
