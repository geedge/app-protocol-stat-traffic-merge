package com.zdjizhi;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPath;
import com.alibaba.fastjson2.JSONReader;
import com.zdjizhi.common.pojo.AppProtocol;
import com.zdjizhi.utils.StringUtil;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author qidaijie
 * @Package com.zdjizhi
 * @Description:
 * @date 2023/4/2116:20
 */
public class FastJsonTest {

    @Test
    public void jsonPathTest() {
        String dataTypeExpr = "[?(@.name = 'traffic_application_protocol_stat')]";
        String value = "{\"fields\":{\"c2s_bytes\":120953742,\"c2s_fragments\":0,\"c2s_pkts\":513665,\"c2s_tcp_lost_bytes\":13000,\"c2s_tcp_ooorder_pkts\":7,\"c2s_tcp_retransmitted_bytes\":89555044,\"c2s_tcp_retransmitted_pkts\":240585,\"in_bytes\":64959358,\"in_pkts\":396214,\"out_bytes\":166012,\"out_pkts\":166012,\"s2c_bytes\":28703159,\"s2c_fragments\":0,\"s2c_pkts\":48561,\"s2c_tcp_lost_bytes\":0,\"s2c_tcp_ooorder_pkts\":377,\"s2c_tcp_retransmitted_bytes\":72122,\"s2c_tcp_retransmitted_pkts\":166,\"sessions\":32148},\"name\":\"traffic_application_protocol_stat\",\"tags\":{\"app_full_path\":\"dns\",\"device_id\":\"9800165603247024\",\"device_group\":\"group-xxg-tsgx\",\"vsys_id\":23,\"data_center\":\"center-xxg-tsgx\",\"protocol_label\":\"ETHERNET.IPv4.UDP\"},\"timestamp\":1682046260}";
        JSONPath dataTypePath = JSONPath.of(dataTypeExpr);

        JSONReader parser = JSONReader.of(value);
        Object result = dataTypePath.extract(parser);
        if (result != null) {
            System.out.println(result.toString());
        }


        Object eval = JSONPath.eval(value, dataTypeExpr);
        if (eval != null) {
            System.out.println(eval.toString());
        }

        System.out.println(JSONPath.contains(value, dataTypeExpr));
    }

    @Test
    public void jsonTest() {
        String message = "{\"fields\":{\"c2s_bytes\":120953742,\"c2s_fragments\":0,\"c2s_pkts\":513665,\"c2s_tcp_lost_bytes\":13000,\"c2s_tcp_ooorder_pkts\":7,\"c2s_tcp_retransmitted_bytes\":89555044,\"c2s_tcp_retransmitted_pkts\":240585,\"in_bytes\":64959358,\"in_pkts\":396214,\"out_bytes\":166012,\"out_pkts\":166012,\"s2c_bytes\":28703159,\"s2c_fragments\":0,\"s2c_pkts\":48561,\"s2c_tcp_lost_bytes\":0,\"s2c_tcp_ooorder_pkts\":377,\"s2c_tcp_retransmitted_bytes\":72122,\"s2c_tcp_retransmitted_pkts\":166,\"sessions\":32148},\"name\":\"traffic_application_protocol_stat\",\"tags\":{\"app_full_path\":\"dns\",\"device_id\":\"9800165603247024\",\"device_group\":\"group-xxg-tsgx\",\"vsys_id\":23,\"data_center\":\"center-xxg-tsgx\",\"protocol_label\":\"ETHERNET.IPv4.UDP\"},\"timestamp\":1682046260}";

        JSONObject originalLog = JSON.parseObject(message);
        JSONObject fieldsObject = JSONObject.parseObject(originalLog.getString("fields"));
        JSONObject tagsObject = JSONObject.parseObject(originalLog.getString("tags"));

        tagsObject.putAll(fieldsObject);

        AppProtocol appProtocol = JSON.to(AppProtocol.class, tagsObject);
        System.out.println(JSONObject.toJSONString(appProtocol));

        System.out.println(appProtocol.getApp_name());
        System.out.println(appProtocol.getProtocol_stack_id());

        appProtocol.setApp_name("123");
        appProtocol.setProtocol_stack_id("abc");
        System.out.println(appProtocol.getApp_name());
        System.out.println(appProtocol.getProtocol_stack_id());
    }
}
