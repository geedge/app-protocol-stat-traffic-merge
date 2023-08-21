package com.zdjizhi.utils.functions.keyby;

import com.zdjizhi.common.pojo.Fields;
import com.zdjizhi.common.pojo.Tags;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

import java.sql.Timestamp;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.functions
 * @Description:
 * @date 2021/7/2112:13
 */
public class DimensionKeyBy implements KeySelector<Tuple3<Tags, Fields, Long>, String> {

    @Override
    public String getKey(Tuple3<Tags, Fields, Long> value) throws Exception {
        //以map拼接的key分组
        return value.f0.toString();
    }
}
