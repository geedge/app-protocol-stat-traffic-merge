package com.zdjizhi;

import com.alibaba.fastjson2.*;
import org.junit.Test;

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

        String message = "{\"common_schema_type\":\"HTTP\",\"common_sessions\":1,\"http_request_line\":\"GET sampleFile.html HTTP/1.1\",\"http_host\":\"www.texaslotto.com\",\"http_url\":\"www.texaslotto.com/sampleFile.html\",\"http_user_agent\":\"xPTS/2.0\",\"http_response_line\":\"HTTP/1.1 200 OK\",\"http_isn\":1953597368,\"http_proxy_flag\":0,\"http_version\":\"http1\",\"http_response_latency_ms\":1,\"http_session_duration_ms\":2,\"http_response_content_type\":\"text/html\",\"http_sequence\":80,\"common_protocol_label\":\"ETHERNET.IPv4.UDP.GTP.IPv4.TCP\",\"common_c2s_byte_diff\":17200,\"common_c2s_pkt_diff\":120,\"common_s2c_byte_diff\":16490,\"common_s2c_pkt_diff\":81,\"common_c2s_ipfrag_num\":0,\"common_s2c_ipfrag_num\":0,\"common_first_ttl\":64,\"common_c2s_tcp_unorder_num\":0,\"common_s2c_tcp_unorder_num\":0,\"common_c2s_tcp_lostlen\":0,\"common_s2c_tcp_lostlen\":0,\"common_c2s_pkt_retrans\":0,\"common_s2c_pkt_retrans\":0,\"common_c2s_byte_retrans\":0,\"common_s2c_byte_retrans\":0,\"common_flags\":24720,\"common_flags_identify_info\":\"{\\\"Server is Local\\\":1,\\\"Inbound\\\":201,\\\"C2S\\\":1,\\\"S2C\\\":2}\",\"common_direction\":73,\"common_app_full_path\":\"http\",\"common_app_label\":\"http\",\"common_tcp_client_isn\":1953597368,\"common_tcp_server_isn\":1950649408,\"common_server_ip\":\"192.50.199.25\",\"common_client_ip\":\"192.50.146.197\",\"common_server_port\":80,\"common_client_port\":22533,\"common_stream_dir\":3,\"common_address_type\":4,\"common_address_list\":\"IPv4_TCP<22533-80-192.50.146.197-192.50.199.25>|GTP<111001144-851056526>|IPv4_UDP<2152-2152-192.50.235.220-192.50.135.83>|MAC<000c299b2fa4-000c2915b4f4>\",\"common_start_time\":1680475247,\"common_end_time\":1680475247,\"common_con_duration_ms\":23,\"common_s2c_pkt_num\":81,\"common_s2c_byte_num\":16490,\"common_c2s_pkt_num\":120,\"common_c2s_byte_num\":17200,\"common_establish_latency_ms\":2,\"common_client_location\":\"日本.Unknown.Unknown\",\"common_server_location\":\"日本.Unknown.Unknown\",\"common_service_category\":[6223,6219,5093,5089],\"common_apn\":\"cmiott.owflr.mcto60g.com\",\"common_imsi\":\"460045157091460\",\"common_imei\":\"8626070583005833\",\"common_phone_number\":\"861440152028973\",\"common_tunnel_endpoint_a_desc\":\"test_50_gtp\",\"common_tunnel_endpoint_b_desc\":\"test_50_gtp\",\"common_tunnels\":[{\"tunnels_schema_type\":\"GTP\",\"gtp_a2b_teid\":111001144,\"gtp_b2a_teid\":851056526,\"gtp_endpoint_a_ip\":\"192.50.235.220\",\"gtp_endpoint_b_ip\":\"192.50.135.83\",\"gtp_endpoint_a_port\":2152,\"gtp_endpoint_b_port\":2152},{\"tunnels_schema_type\":\"MULTIPATH_ETHERNET\",\"c2s_source_mac\":\"00:0c:29:9b:2f:a4\",\"c2s_destination_mac\":\"00:0c:29:15:b4:f4\",\"s2c_source_mac\":\"00:0c:29:15:b4:f4\",\"s2c_destination_mac\":\"00:0c:29:9b:2f:a4\"}],\"common_stream_trace_id\":\"578829229323951427\",\"common_l4_protocol\":\"IPv4_TCP\",\"common_sled_ip\":\"192.168.40.161\",\"common_device_id\":\"unknown\",\"common_device_tag\":\"{\\\"tags\\\":[{\\\"tag\\\":\\\"device_group\\\",\\\"value\\\":\\\"group-xxg-7400\\\"},{\\\"tag\\\":\\\"data_center\\\",\\\"value\\\":\\\"center-xxg-7400\\\"}]}\",\"common_t_vsys_id\":1,\"common_policy_id\":0,\"common_service\":2,\"common_action\":0,\"common_vsys_id\":1}";
        JSONObject originalLog = JSON.parseObject(message);

        String value1 = originalLog.getString("common_device_tag");
        System.out.println(JSONPath.eval(value1, "$.tags[?(@.tag=='data_center')][0].value"));
        System.out.println(JSONPath.of("$.tags[?(@.tag=='data_center')].value").extract(JSONReader.of(value1)));

        JSONArray jsonArray = originalLog.getJSONObject("common_device_tag").getJSONArray("tags");
        for (Object array : jsonArray) {
            JSONObject object = JSON.parseObject(array.toString());
            if ("device_group".equals(object.getString("tag"))) {
                System.out.println(object.getString("value"));
            }
        }

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
