package com.zdjizhi.utils.functions.map;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zdjizhi.common.pojo.Fields;
import com.zdjizhi.common.pojo.Tags;
import com.zdjizhi.utils.StringUtil;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;


/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.functions
 * @Description:
 * @date 2021/5/2715:01
 */
public class MetricsParseMap implements MapFunction<String, Tuple2<Tags, Fields>> {
    private static final Log logger = LogFactory.get();

    @Override
    public Tuple2<Tags, Fields> map(String message) {
        try {
            JSONObject originalLog = JSON.parseObject(message);
            Fields fields = JSONObject.parseObject(originalLog.getString("fields"), Fields.class);
            Tags tags = JSONObject.parseObject(originalLog.getString("tags"), Tags.class);

            String appFullPath = tags.getApp_name();
            if (StringUtil.isNotBlank(appFullPath)) {
                String appName = appFullPath.substring(appFullPath.lastIndexOf(".") + 1);
                String protocolLabel = tags.getProtocol_stack_id();

                tags.setApp_name(appName);
                tags.setProtocol_stack_id(protocolLabel.concat(".").concat(appFullPath));
            }

            return new Tuple2<>(tags, fields);
        } catch (RuntimeException e) {
            logger.error("An error occurred in the original log parsing reorganization,error message is:" + e);
            return new Tuple2<>(null, null);
        }
    }

}
