package com.zdjizhi.common.config;


import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * @author Administrator
 */
public class GlobalConfig {

    private static StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

    static {
        encryptor.setPassword("galaxy");
    }

    /**
     * 协议分隔符，需要转义
     */
    public static final String PROTOCOL_SPLITTER = "\\.";


    /**
     * System
     */
    public static final Integer SOURCE_PARALLELISM = GlobalConfigLoad.getIntProperty(0, "source.parallelism");
    public static final Integer PARSE_PARALLELISM = GlobalConfigLoad.getIntProperty(0, "parse.parallelism");
    public static final Integer WINDOW_PARALLELISM = GlobalConfigLoad.getIntProperty(0, "window.parallelism");
    public static final Integer COUNT_WINDOW_TIME = GlobalConfigLoad.getIntProperty(0, "count.window.time");
    public static final String TOOLS_LIBRARY = GlobalConfigLoad.getStringProperty(0, "tools.library");
    public static final Integer SINK_PARALLELISM = GlobalConfigLoad.getIntProperty(0, "sink.parallelism");

    /**
     * Kafka common
     */
    public static final String KAFKA_SASL_JAAS_USER = encryptor.decrypt(GlobalConfigLoad.getStringProperty(1, "kafka.user"));
    public static final String KAFKA_SASL_JAAS_PIN = encryptor.decrypt(GlobalConfigLoad.getStringProperty(1, "kafka.pin"));


    /**
     * kafka sink config
     */
    public static final String SINK_KAFKA_SERVERS = GlobalConfigLoad.getStringProperty(0, "sink.kafka.servers");
    public static final String SINK_KAFKA_TOPIC = GlobalConfigLoad.getStringProperty(0, "sink.kafka.topic");
    public static final String PRODUCER_ACK = GlobalConfigLoad.getStringProperty(1, "producer.ack");
    public static final String RETRIES = GlobalConfigLoad.getStringProperty(1, "retries");
    public static final String LINGER_MS = GlobalConfigLoad.getStringProperty(1, "linger.ms");
    public static final Integer REQUEST_TIMEOUT_MS = GlobalConfigLoad.getIntProperty(1, "request.timeout.ms");
    public static final Integer BATCH_SIZE = GlobalConfigLoad.getIntProperty(1, "batch.size");
    public static final Integer BUFFER_MEMORY = GlobalConfigLoad.getIntProperty(1, "buffer.memory");
    public static final Integer MAX_REQUEST_SIZE = GlobalConfigLoad.getIntProperty(1, "max.request.size");


    /**
     * kafka source config
     */
    public static final String SOURCE_KAFKA_SERVERS = GlobalConfigLoad.getStringProperty(0, "source.kafka.servers");
    public static final String SOURCE_KAFKA_TOPIC = GlobalConfigLoad.getStringProperty(0, "source.kafka.topic");
    public static final String GROUP_ID = GlobalConfigLoad.getStringProperty(0, "group.id");
    public static final String SESSION_TIMEOUT_MS = GlobalConfigLoad.getStringProperty(1, "session.timeout.ms");
    public static final String MAX_POLL_RECORDS = GlobalConfigLoad.getStringProperty(1, "max.poll.records");
    public static final String MAX_PARTITION_FETCH_BYTES = GlobalConfigLoad.getStringProperty(1, "max.partition.fetch.bytes");


    /**
     * kafka限流配置-20201117
     */
    public static final String PRODUCER_KAFKA_COMPRESSION_TYPE = GlobalConfigLoad.getStringProperty(1, "producer.kafka.compression.type");


}