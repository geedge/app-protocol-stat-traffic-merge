package com.zdjizhi;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zdjizhi.common.pojo.Fields;
import com.zdjizhi.common.pojo.Tags;
import org.junit.Test;

/**
 * @author qidaijie
 * @Package com.zdjizhi
 * @Description:
 * @date 2024/3/2614:14
 */
public class LegacyFieldTest {
    @Deprecated
    private static final String LEGACY_PROTOCOL_KEY_NAME = "protocol_label";

    @Deprecated
    private static final String LEGACY_APP_KEY_NAME = "app_full_path";

    @Test
    public void test() {
        //23.07 version data
        String message = "{\"fields\":{\"s2c_tcp_retransmitted_pkts\":0,\"sessions\":0},\"name\":\"application_protocol_stat\",\"tags\":{\"data_center\":\"center-xxg-tsgx\",\"device_group\":\"group-xxg-tsgx\",\"device_id\":\"9800165603191151\",\"protocol_label\":\"ETHERNET.IPv4.UDP\",\"app_full_path\":\"google\",\"vsys_id\":1},\"timestamp_ms\":1705907560000}";
        //24.02 version data
        //String message = "{\"fields\":{\"s2c_tcp_retransmitted_pkts\":0,\"sessions\":0},\"name\":\"application_protocol_stat\",\"tags\":{\"data_center\":\"center-xxg-tsgx\",\"device_group\":\"group-xxg-tsgx\",\"device_id\":\"9800165603191151\",\"decoded_path\":\"ETHERNET.IPv4.UDP\","app":"google",\"vsys_id\":1},\"timestamp_ms\":1705907560000}";
        JSONObject originalLog = JSON.parseObject(message);
        supportingLegacyField(originalLog);
        Fields fields = JSONObject.parseObject(originalLog.getString("fields"), Fields.class);
        Tags tags = JSONObject.parseObject(originalLog.getString("tags"), Tags.class);

        JSONObject from = JSONObject.from(tags);
        System.out.println(from.toJSONString());
    }


    private static void supportingLegacyField(JSONObject originalLog) {
        JSONObject tags = originalLog.getJSONObject("tags");
        System.out.println("解析前tags数据：" + tags.toJSONString() + "\n");

        if (tags.containsKey(LEGACY_PROTOCOL_KEY_NAME)) {
            tags.put("decoded_path", tags.remove(LEGACY_PROTOCOL_KEY_NAME));

            tags.put("app", tags.remove(LEGACY_APP_KEY_NAME));

            originalLog.put("tags", originalLog.remove("tags"));
            System.out.println("转换后tags数据：" + tags.toJSONString() + "\n");
        }
    }
}
