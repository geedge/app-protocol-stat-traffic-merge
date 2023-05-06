package com.zdjizhi.utils.functions.keyby;

import com.alibaba.fastjson2.JSONObject;
import com.zdjizhi.common.pojo.AppProtocol;
import com.zdjizhi.common.pojo.Fields;
import com.zdjizhi.common.pojo.Tags;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

import java.util.Map;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.functions
 * @Description:
 * @date 2021/7/2112:13
 */
public class DimensionKeyBy implements KeySelector<Tuple2<String, AppProtocol>, String> {

    @Override
    public String getKey(Tuple2<String, AppProtocol> value) throws Exception {
        //以map拼接的key分组
        return value.f0;
    }
}
