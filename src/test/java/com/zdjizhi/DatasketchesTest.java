package com.zdjizhi;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.*;
import com.zdjizhi.utils.JsonMapper;
import org.apache.datasketches.hll.HllSketch;
import org.apache.datasketches.hll.TgtHllType;
import org.apache.datasketches.hll.Union;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.lang.instrument.Instrumentation;
import java.util.*;

/**
 * @author qidaijie
 * @Package com.zdjizhi
 * @Description:
 * @date 2023/3/217:17
 */
public class DatasketchesTest {

    @Test
    public void HllSketchTest() {
        HashSet<String> strings = new HashSet<>();

        HllSketch sketch = new HllSketch(12);

        for (int i = 0; i < 50; i++) {
            String ip = "192.168.1." + i;
            sketch.update(ip);
            strings.add(ip);
        }

        System.out.println(sketch.getEstimate() + "--" + strings.size());

        HashSet<String> randomStrings = new HashSet<>();

        HllSketch randomSketch = new HllSketch(12);

        for (int i = 0; i < 50; i++) {
            String ip = makeIPv4Random();
            randomSketch.update(ip);
            randomStrings.add(ip);
        }

        System.out.println(randomSketch.getEstimate() + "--" + randomStrings.size());
    }

    @Test
    public void HllSketchUnionTest() {
        HashSet<String> strings = new HashSet<>();

        HllSketch sketch = new HllSketch(12);

        for (int i = 0; i < 50; i++) {
            String ip = "192.168.1." + i;
            sketch.update(ip);
            strings.add(ip);
        }

        HllSketch sketch2 = new HllSketch(12);

        for (int i = 0; i < 10; i++) {
            String ip = "192.168.2." + i;
            sketch2.update(ip);
            strings.add(ip);
        }

        Union union = new Union(12);

        union.update(sketch);
        union.update(sketch2);
        HllSketch sketch_result = HllSketch.heapify(union.getResult().toCompactByteArray());

        System.out.println(sketch.getEstimate() + "--" + strings.size());
        System.out.println(sketch2.getEstimate() + "--" + strings.size());
        System.out.println(sketch_result.getEstimate() + "--" + strings.size());
    }

    @Test
    public void HllSketchDruidTest() {
        HashMap<String, Object> dataMap = new HashMap<>();

        HashSet<String> strings = new HashSet<>();

        HllSketch sketch = new HllSketch(12);

        for (int i = 0; i < 50; i++) {
            String ip = "192.168.1." + i;
            sketch.update(ip);
            strings.add(ip);
        }

        HllSketch sketch2 = new HllSketch(12);

        for (int i = 0; i < 10; i++) {
            String ip = "192.168.2." + i;
            sketch2.update(ip);
            strings.add(ip);
        }

        Union union = new Union(12);

        union.update(sketch);
        union.update(sketch2);
        HllSketch sketch_result1 = HllSketch.heapify(union.getResult().toCompactByteArray());

        HllSketch sketch3 = new HllSketch(12);

        for (int i = 0; i < 10; i++) {
            String ip = "192.168.3." + i;
            sketch3.update(ip);
            strings.add(ip);
        }

        Union union2 = new Union(12);

        union2.update(sketch_result1);
        union2.update(sketch3);
        HllSketch sketch_result2 = HllSketch.heapify(union2.getResult().toCompactByteArray());

        System.out.println(sketch.getEstimate() + "--" + strings.size());
        System.out.println(sketch2.getEstimate() + "--" + strings.size());
        System.out.println(sketch3.getEstimate() + "--" + strings.size());
        System.out.println(sketch_result1.getEstimate() + "--" + strings.size());
        System.out.println(sketch_result2.getEstimate() + "--" + strings.size());

        Result result = new Result();
        result.setC2s_pkt_num(10);
        result.setS2c_pkt_num(10);
        result.setC2s_byte_num(10);
        result.setS2c_byte_num(10);
        result.setStat_time(1679970031);
        result.setSchema_type("HLLSketchMergeTest");

        //CompactByte
        result.setIp_object(sketch_result2.toCompactByteArray());
//        System.out.println(result.toString());
        //sendMessage(JsonMapper.toJsonString(result);


        //UpdatableByte
        result.setIp_object(sketch_result2.toUpdatableByteArray());
//        System.out.println(result.toString());
        //sendMessage(JsonMapper.toJsonString(result);

        //Hashmap
        dataMap.put("app_name", "TEST");
        dataMap.put("protocol_stack_id", "HTTP");
        dataMap.put("vsys_id", 1);
        dataMap.put("stat_time", 1681370100);
        dataMap.put("client_ip_sketch", sketch_result2.toUpdatableByteArray());

        System.out.println("Jackson:" + JsonMapper.toJsonString(dataMap));
        System.out.println("FastJson2:" + JSONObject.toJSONString(dataMap));
        System.out.println("Hutool:" + JSONUtil.toJsonStr(dataMap) + "\n\n");


        dataMap.put("client_ip_sketch", Base64.getEncoder().encode(sketch_result2.toUpdatableByteArray()));
        System.out.println("FastJson2 Byte(Base64):" + JSONObject.toJSONString(dataMap));
        System.out.println("Hutool Byte(Base64):" + JSONObject.toJSONString(dataMap));
        System.out.println(JSONUtil.toJsonStr(dataMap));

//        sendMessage(JSONObject.toJSONString(dataMap));
    }

    @Test
    public void HllSketchStorageTest() {
        TgtHllType hllType = TgtHllType.HLL_4;
//        TgtHllType hllType = TgtHllType.HLL_6;
//        TgtHllType hllType = TgtHllType.HLL_8;

        HllSketch sketch4 = new HllSketch(4,hllType);
        HllSketch sketch8 = new HllSketch(8,hllType);
        HllSketch sketch12 = new HllSketch(12,hllType);
        HllSketch sketch16 = new HllSketch(16,hllType);
        HllSketch sketch21 = new HllSketch(21,hllType);

        HashSet<String> IPSet = new HashSet<>();

        for (int i = 0; i < 500000; i++) {
            String ip = makeIPv4Random();
            IPSet.add(ip);
            sketch4.update(ip);
            sketch8.update(ip);
            sketch12.update(ip);
            sketch16.update(ip);
            sketch21.update(ip);
        }
        System.out.println(IPSet.size());
        System.out.println(sketch4.toString());
        System.out.println(sketch8.toString());
        System.out.println(sketch12.toString());
        System.out.println(sketch16.toString());
        System.out.println(sketch21.toString());

    }


    //随机生成ip
    private static String makeIPv4Random() {
        int v4_1 = new Random().nextInt(255) + 1;
        int v4_2 = new Random().nextInt(100);
        int v4_3 = new Random().nextInt(100);
        int v4_4 = new Random().nextInt(255);
        return v4_1 + "." + v4_2 + "." + v4_3 + "." + v4_4;
    }

    private static void sendMessage(Object message) {
        Properties props = new Properties();
        //kafka地址
        props.put("bootstrap.servers", "192.168.44.12:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 67108864);
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        KafkaProducer<String, Object> kafkaProducer = new KafkaProducer<String, Object>(props);

        kafkaProducer.send(new ProducerRecord<String, Object>("TRAFFIC-PROTOCOL-TEST", message));

        kafkaProducer.close();
    }
}

class Result {

    private String schema_type;
    private long c2s_byte_num;
    private long c2s_pkt_num;
    private long s2c_byte_num;
    private long s2c_pkt_num;
    private long stat_time;
    private byte[] ip_object;

    public void setSchema_type(String schema_type) {
        this.schema_type = schema_type;
    }

    public void setC2s_byte_num(long c2s_byte_num) {
        this.c2s_byte_num = c2s_byte_num;
    }

    public void setC2s_pkt_num(long c2s_pkt_num) {
        this.c2s_pkt_num = c2s_pkt_num;
    }

    public void setS2c_byte_num(long s2c_byte_num) {
        this.s2c_byte_num = s2c_byte_num;
    }

    public void setS2c_pkt_num(long s2c_pkt_num) {
        this.s2c_pkt_num = s2c_pkt_num;
    }

    public void setStat_time(long stat_time) {
        this.stat_time = stat_time;
    }

    public void setIp_object(byte[] ip_object) {
        this.ip_object = ip_object;
    }

    @Override
    public String toString() {
        return "Result{" +
                "schema_type='" + schema_type + '\'' +
                ", c2s_byte_num=" + c2s_byte_num +
                ", c2s_pkt_num=" + c2s_pkt_num +
                ", s2c_byte_num=" + s2c_byte_num +
                ", s2c_pkt_num=" + s2c_pkt_num +
                ", stat_time=" + stat_time +
                ", ip_object=" + Arrays.toString(ip_object) +
                '}';
    }
}