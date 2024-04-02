package com.zdjizhi.utils.functions.process;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPath;
import com.zdjizhi.common.pojo.Fields;
import com.zdjizhi.common.pojo.Tags;
import com.zdjizhi.utils.StringUtil;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

public class ParsingData extends ProcessFunction<String, Tuple3<Tags, Fields, Long>> {
    private static final Log logger = LogFactory.get();

    /**
     * 适配TSG 24.02日志重组前数据结构，待过期后删除此处代码
     */
    @Deprecated
    private static final String LEGACY_PROTOCOL_KEY_NAME = "protocol_label";

    /**
     * 适配TSG 24.02日志重组前数据结构，待过期后删除此处代码
     */
    @Deprecated
    private static final String LEGACY_APP_KEY_NAME = "app_full_path";

    private static final String dataTypeExpr = "[?(@.name = 'traffic_application_protocol_stat')]";

    @Override
    public void processElement(String value, ProcessFunction<String, Tuple3<Tags, Fields, Long>>.Context ctx, Collector<Tuple3<Tags, Fields, Long>> out) {
        try {
            if (StringUtil.isNotBlank(value)) {
                Object isProtocolData = JSONPath.eval(value, dataTypeExpr);
                if (isProtocolData != null) {
                    JSONObject originalLog = JSON.parseObject(value);

                    //适配TSG 24.02日志重组前数据结构，待过期后删除此处代码
                    supportingLegacyField(originalLog);

                    Fields fields = JSONObject.parseObject(originalLog.getString("fields"), Fields.class);
                    Tags tags = JSONObject.parseObject(originalLog.getString("tags"), Tags.class);
                    Long timestamp_ms = originalLog.getLong("timestamp_ms");

                    if (StringUtil.isNotBlank(tags.getProtocol_stack_id())) {
                        joinProtocol(tags);

                        out.collect(new Tuple3<>(tags, fields, timestamp_ms));
                    }
                }
            }
        } catch (RuntimeException e) {
            logger.error("Parsing application_protocol_stat data is abnormal! The exception message is: {}", e.getMessage());
        }
    }

    /**
     * 适配TSG 24.02日志重组前数据结构，在不改变原有逻辑情况下将
     * protocol_label 修改为 decoded_path
     * app_full_path 修改为 app
     * <p>
     * 待过期后删除此处代码
     *
     * @param originalLog 原始Metrics日志
     */
    @Deprecated
    private static void supportingLegacyField(JSONObject originalLog) {
        JSONObject tags = originalLog.getJSONObject("tags");
        if (tags.containsKey(LEGACY_PROTOCOL_KEY_NAME)) {
            tags.put("decoded_path", tags.remove(LEGACY_PROTOCOL_KEY_NAME));

            tags.put("app", tags.remove(LEGACY_APP_KEY_NAME));

            originalLog.put("tags", originalLog.remove("tags"));
        }
    }

    /**
     * 避免计算重复的协议，去除Decoded Path（最后一个元素） 与 Application（第一个元素）重复的基础协议。
     *
     * @param tags
     */
    private static void joinProtocol(Tags tags) {
        String appFullPath = tags.getApp_name();

        if (StringUtil.isNotBlank(appFullPath)) {
            String appName = appFullPath.substring(appFullPath.lastIndexOf(".") + 1);
            tags.setApp_name(appName);

            String protocolLabel = tags.getProtocol_stack_id();

            String endProtocol = protocolLabel.substring(protocolLabel.lastIndexOf(".") + 1);

            String[] appSplits = appFullPath.split("\\.");

            String firstAppProtocol = appSplits[0];

            if (endProtocol.equals(firstAppProtocol)) {
                tags.setProtocol_stack_id(protocolLabel.substring(0, protocolLabel.lastIndexOf(".")).concat(".").concat(appFullPath));
            } else {
                tags.setProtocol_stack_id(protocolLabel.concat(".").concat(appFullPath));
            }
        }
    }
}
