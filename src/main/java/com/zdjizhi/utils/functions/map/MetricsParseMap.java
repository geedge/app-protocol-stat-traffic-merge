package com.zdjizhi.utils.functions.map;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zdjizhi.common.pojo.AppProtocol;
import com.zdjizhi.utils.StringUtil;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;


/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.functions
 * @Description:
 * @date 2021/5/2715:01
 */
public class MetricsParseMap implements MapFunction<String, Tuple2<String, AppProtocol>> {
    private static final Log logger = LogFactory.get();

    @Override
    @SuppressWarnings("unchecked")
    public Tuple2<String, AppProtocol> map(String message) {
        try {
            JSONObject originalLog = JSON.parseObject(message);
            JSONObject fieldsObject = JSONObject.parseObject(originalLog.getString("fields"));
            JSONObject tagsObject = JSONObject.parseObject(originalLog.getString("tags"));
            fieldsObject.putAll(tagsObject);

            AppProtocol appProtocol = JSON.to(AppProtocol.class, fieldsObject);

            String appFullPath = appProtocol.getApp_name();
            if (StringUtil.isNotBlank(appFullPath)) {
                String appName = appFullPath.substring(appFullPath.lastIndexOf(".") + 1);
                String protocolLabel = appProtocol.getProtocol_stack_id();

                appProtocol.setApp_name(appName);
                appProtocol.setProtocol_stack_id(protocolLabel.concat(".").concat(appFullPath));
            }

            return new Tuple2<>(tagsObject.toJSONString(), appProtocol);
        } catch (RuntimeException e) {
            logger.error("An error occurred in the original log parsing reorganization,error message is:" + e);
            return new Tuple2<>(null, null);
        }
    }

}
