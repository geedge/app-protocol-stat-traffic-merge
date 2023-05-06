package com.zdjizhi.utils.kafka;

import com.zdjizhi.common.config.GlobalConfig;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.kafka
 * @Description:
 * @date 2021/6/813:54
 */
public class KafkaConsumer {
    private static Properties createConsumerConfig() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", GlobalConfig.SOURCE_KAFKA_SERVERS);
        properties.put("group.id", GlobalConfig.GROUP_ID);
        properties.put("session.timeout.ms", GlobalConfig.SESSION_TIMEOUT_MS);
        properties.put("max.poll.records", GlobalConfig.MAX_POLL_RECORDS);
        properties.put("max.partition.fetch.bytes", GlobalConfig.MAX_PARTITION_FETCH_BYTES);
        properties.put("partition.discovery.interval.ms", "10000");

        CertUtils.chooseCert(GlobalConfig.SOURCE_KAFKA_SERVERS, properties);

        return properties;
    }

    /**
     * 官方序列化kafka数据
     *
     * @return kafka logs
     */
    public static FlinkKafkaConsumer<String> getKafkaConsumer() {
        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>(GlobalConfig.SOURCE_KAFKA_TOPIC,
                new SimpleStringSchema(), createConsumerConfig());

        //随着checkpoint提交，将offset提交到kafka
        kafkaConsumer.setCommitOffsetsOnCheckpoints(true);

        //从消费组当前的offset开始消费
        kafkaConsumer.setStartFromGroupOffsets();

        return kafkaConsumer;
    }
}
