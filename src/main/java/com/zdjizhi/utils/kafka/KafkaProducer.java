package com.zdjizhi.utils.kafka;

import com.zdjizhi.common.config.GlobalConfig;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Optional;
import java.util.Properties;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.kafka
 * @Description:
 * @date 2021/6/814:04
 */
public class KafkaProducer {

    private static Properties createProducerConfig() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", GlobalConfig.SINK_KAFKA_SERVERS);
        properties.put("acks", GlobalConfig.PRODUCER_ACK);
        properties.put("retries", GlobalConfig.RETRIES);
        properties.put("linger.ms", GlobalConfig.LINGER_MS);
        properties.put("request.timeout.ms", GlobalConfig.REQUEST_TIMEOUT_MS);
        properties.put("batch.size", GlobalConfig.BATCH_SIZE);
        properties.put("buffer.memory", GlobalConfig.BUFFER_MEMORY);
        properties.put("max.request.size", GlobalConfig.MAX_REQUEST_SIZE);
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, GlobalConfig.PRODUCER_KAFKA_COMPRESSION_TYPE);

        CertUtils.chooseCert(GlobalConfig.SINK_KAFKA_SERVERS, properties);

        return properties;
    }


    public static FlinkKafkaProducer<String> getKafkaProducer() {
        FlinkKafkaProducer<String> kafkaProducer = new FlinkKafkaProducer<String>(
                GlobalConfig.SINK_KAFKA_TOPIC,
                new SimpleStringSchema(),
                createProducerConfig(), Optional.empty());

        //启用此选项将使生产者仅记录失败日志而不是捕获和重新抛出它们
        kafkaProducer.setLogFailuresOnly(true);

        return kafkaProducer;
    }
}
