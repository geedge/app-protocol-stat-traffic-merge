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
}
