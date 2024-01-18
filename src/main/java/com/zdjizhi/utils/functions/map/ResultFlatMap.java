package com.zdjizhi.utils.functions.map;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson2.JSONObject;
import com.zdjizhi.common.pojo.Metrics;
import com.zdjizhi.common.pojo.Tags;
import com.zdjizhi.utils.StringUtil;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.functions
 * @Description:
 * @date 2021/7/2114:52
 */
public class ResultFlatMap implements FlatMapFunction<Metrics, String> {
    private static final Log logger = LogFactory.get();
    /**
     * 协议分隔符，需要转义
     */
    private static final String PROTOCOL_SPLITTER = "\\.";


    @Override
    public void flatMap(Metrics metrics, Collector<String> out) {
        try {
            Tags tags = metrics.getTags();
            String protocolStackId = tags.getProtocol_stack_id();
            out.collect(getResultJson(metrics));
            tags.setApp_name(null);

            StringBuilder stringBuilder = new StringBuilder();
            String[] protocolIds = protocolStackId.split(PROTOCOL_SPLITTER);
            int protocolIdsNum = protocolIds.length;
            for (int i = 0; i < protocolIdsNum - 1; i++) {
                if (StringUtil.isBlank(stringBuilder.toString())) {
                    stringBuilder.append(protocolIds[i]);
                    tags.setProtocol_stack_id(stringBuilder.toString());
                    metrics.setTags(tags);
                    out.collect(getResultJson(metrics));
                } else {
                    stringBuilder.append(".").append(protocolIds[i]);
                    tags.setProtocol_stack_id(stringBuilder.toString());
                    metrics.setTags(tags);
                    out.collect(getResultJson(metrics));
                }
            }
        } catch (RuntimeException e) {
            logger.error("An exception occurred during parsing the result data,error message is:" + e);
            e.printStackTrace();
        }
    }

    private static String getResultJson(Metrics metrics) {
        return JSONObject.toJSONString(metrics);
    }
}
