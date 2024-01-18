package com.zdjizhi.conf;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;

public class FusionConfigs {
    /**
     * The prefix for Kafka properties used in the source.
     */
    public static final String SOURCE_KAFKA_PROPERTIES_PREFIX = "source.kafka.props.";

    /**
     * The prefix for Kafka properties used in the sink.
     */
    public static final String SINK_KAFKA_PROPERTIES_PREFIX = "sink.kafka.props.";
    /**
     * Configuration option for the Kafka topic used in the source.
     */
    public static final ConfigOption<String> SOURCE_KAFKA_TOPIC =
            ConfigOptions.key("source.kafka.topic")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("The Kafka topic used in the source.");

    /**
     * Configuration option for the Kafka topic used in the sink.
     */
    public static final ConfigOption<String> SINK_KAFKA_TOPIC =
            ConfigOptions.key("sink.kafka.topic")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("The Kafka topic used in the sink.");

}
