package com.zdjizhi.utils.functions.filter;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson2.JSONPath;
import com.alibaba.fastjson2.JSONReader;
import com.zdjizhi.utils.StringUtil;
import org.apache.flink.api.common.functions.FilterFunction;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.functions.filter
 * @Description:
 * @date 2023/4/1919:02
 */
public class DataTypeFilter implements FilterFunction<String> {
    private static final Log logger = LogFactory.get();

    private static final String dataTypeExpr = "[?(@.name = 'traffic_application_protocol_stat')]";

    @Override
    public boolean filter(String message) throws Exception {
        boolean protocolData = false;
        try {
            if (StringUtil.isNotBlank(message)) {
                Object name = JSONPath.eval(message, dataTypeExpr);
                if (name != null) {
                    protocolData = true;
                }
            }
        } catch (RuntimeException e) {
            logger.error("Parsing metric data is abnormal! The exception message is:" + e.getMessage());
        }
        return protocolData;
    }
}
