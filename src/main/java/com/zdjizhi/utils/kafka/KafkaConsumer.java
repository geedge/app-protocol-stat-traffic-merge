package com.zdjizhi.utils.kafka;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.kafka
 * @Description:
 * @date 2021/6/813:54
 */
public class KafkaConsumer {

    /**
     * 官方序列化kafka数据
     *
     * @return kafka logs
     */
    public static FlinkKafkaConsumer<String> getKafkaConsumer(Properties properties, String topic, String startupMode) {

        setDefaultConfig(properties, ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 60000);
        setDefaultConfig(properties, ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5000);
        setDefaultConfig(properties, ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 31457280);

        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(), properties);

        switch (startupMode) {
            case "group":
                kafkaConsumer.setStartFromGroupOffsets();
                break;
            case "latest":
                kafkaConsumer.setStartFromLatest();
                break;
            case "earliest":
                kafkaConsumer.setStartFromEarliest();
                break;
            default:
                kafkaConsumer.setStartFromGroupOffsets();
        }

        return kafkaConsumer;
    }

    private static void setDefaultConfig(Properties properties, String key, Object value) {
        if (!properties.contains(key)) {
            properties.put(key, value);
        }
    }
}
