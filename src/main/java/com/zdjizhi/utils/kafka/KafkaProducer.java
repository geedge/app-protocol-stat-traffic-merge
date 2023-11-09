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
        setDefaultConfig(properties, "ack", 1);
        setDefaultConfig(properties, "retries", 0);
        setDefaultConfig(properties, "linger.ms", 10);
        setDefaultConfig(properties, "request.timeout.ms", 30000);
        setDefaultConfig(properties, "batch.size", 262144);
        setDefaultConfig(properties, "buffer.memory", 134217728);
        setDefaultConfig(properties, "max.request.size", 10485760);
        setDefaultConfig(properties, "compression.type", "snappy");

        FlinkKafkaProducer<String> kafkaProducer = new FlinkKafkaProducer<>(
                topic,
                new SimpleStringSchema(),
                properties, Optional.empty());

        kafkaProducer.setLogFailuresOnly(logFailuresOnly);

        return kafkaProducer;
    }

    private static void setDefaultConfig(Properties properties, String key, Object value) {
        if (!properties.contains(key)) {
            properties.put(key, value);
        }
    }
}
