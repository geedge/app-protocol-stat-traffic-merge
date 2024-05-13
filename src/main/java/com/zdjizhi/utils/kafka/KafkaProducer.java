package com.zdjizhi.utils.kafka;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

import java.util.Optional;
import java.util.Properties;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.kafka
 * @Description:
 * @date 2021/6/814:04
 */
public class KafkaProducer {

    public static FlinkKafkaProducer<String> getKafkaProducer(Properties properties, String topic, boolean logFailuresOnly) {


        FlinkKafkaProducer<String> kafkaProducer = new FlinkKafkaProducer<>(
                topic,
                new SimpleStringSchema(),
                properties, Optional.empty());

        kafkaProducer.setLogFailuresOnly(false);

        return kafkaProducer;
    }

}
