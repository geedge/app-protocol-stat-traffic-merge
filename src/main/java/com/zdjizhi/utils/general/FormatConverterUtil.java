package com.zdjizhi.utils.general;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.zdjizhi.common.pojo.AppProtocol;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.general
 * @Description:
 * @date 2023/5/519:04
 */
public class FormatConverterUtil {

    /**
     * 生成tags类型数据
     *
     * @param appProtocol 结果集
     * @return tags结果
     */
    public static JSONObject getTags(AppProtocol appProtocol) {
        JSONObject tags = new JSONObject();
        tags.fluentPut("vsys_id", appProtocol.getVsys_id())
                .fluentPut("device_id", appProtocol.getDevice_id())
                .fluentPut("device_group", appProtocol.getDevice_group())
                .fluentPut("data_center", appProtocol.getData_center())
                .fluentPut("protocol_stack_id", appProtocol.getProtocol_stack_id())
                .fluentPut("app_name", appProtocol.getApp_name());

        return tags;

    }

    /**
     * 将数据结构转换为最终的结构
     *
     * @param appProtocol 结果集
     * @return 结果数据
     */
    public static JSONObject structureConversion(AppProtocol appProtocol) {
        JSONObject metrics = new JSONObject();
        JSONObject fields = new JSONObject();

        fields.fluentPut("sessions", appProtocol.getSessions())
                .fluentPut("in_bytes", appProtocol.getIn_bytes())
                .fluentPut("out_bytes", appProtocol.getOut_bytes())
                .fluentPut("in_pkts", appProtocol.getIn_pkts())
                .fluentPut("out_pkts", appProtocol.getOut_pkts())
                .fluentPut("c2s_bytes", appProtocol.getC2s_bytes())
                .fluentPut("s2c_bytes", appProtocol.getS2c_bytes())
                .fluentPut("c2s_pkts", appProtocol.getC2s_pkts())
                .fluentPut("s2c_pkts", appProtocol.getS2c_pkts())
                .fluentPut("c2s_fragments", appProtocol.getC2s_fragments())
                .fluentPut("s2c_fragments", appProtocol.getS2c_fragments())
                .fluentPut("c2s_tcp_lost_bytes", appProtocol.getC2s_tcp_lost_bytes())
                .fluentPut("s2c_tcp_lost_bytes", appProtocol.getS2c_tcp_lost_bytes())
                .fluentPut("c2s_tcp_ooorder_pkts", appProtocol.getC2s_tcp_ooorder_pkts())
                .fluentPut("s2c_tcp_ooorder_pkts", appProtocol.getS2c_tcp_ooorder_pkts())
                .fluentPut("c2s_tcp_retransmitted_pkts", appProtocol.getC2s_tcp_retransmitted_bytes())
                .fluentPut("s2c_tcp_retransmitted_pkts", appProtocol.getS2c_tcp_retransmitted_bytes())
                .fluentPut("c2s_tcp_retransmitted_bytes", appProtocol.getC2s_tcp_retransmitted_pkts())
                .fluentPut("s2c_tcp_retransmitted_bytes", appProtocol.getS2c_tcp_retransmitted_pkts())
                .fluentPut("client_ip_sketch", appProtocol.getClient_ip_sketch());

        metrics.put("timestamp", appProtocol.getTimestamp());
        metrics.put("name", "application_protocol_stat");

        metrics.fluentPut("timestamp", appProtocol.getTimestamp())
                .fluentPut("name", "application_protocol_stat")
                .fluentPut("fields", fields);

        return metrics;
    }


    /**
     * 更新结果集tags数据（不同协议层级），并输出json
     *
     * @param conversion 结果集
     * @param tags       tags结果
     * @return 结果json
     */
    public static String updateTagsData(JSONObject conversion, JSONObject tags) {
        conversion.put("tags", tags);

        return JSONObject.toJSONString(conversion
                , JSONWriter.Feature.WriteNullStringAsEmpty
                , JSONWriter.Feature.WriteNullNumberAsZero);
    }

}
