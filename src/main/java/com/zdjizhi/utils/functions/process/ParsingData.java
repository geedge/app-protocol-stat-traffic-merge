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

    private static final String dataTypeExpr = "[?(@.name = 'traffic_application_protocol_stat')]";

    @Override
    public void processElement(String value, ProcessFunction<String, Tuple3<Tags, Fields, Long>>.Context ctx, Collector<Tuple3<Tags, Fields, Long>> out) {
        try {
            if (StringUtil.isNotBlank(value)) {
                Object isProtocolData = JSONPath.eval(value, dataTypeExpr);
                if (isProtocolData != null) {
                    JSONObject originalLog = JSON.parseObject(value);
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
