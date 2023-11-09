package com.zdjizhi.common.config;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;

/**
 * Containing configuration options for the Fusion application.
 *
 * @author chaoc
 * @since 1.0
 */
public class MergeConfigs {

    /**
     * The prefix for Kafka properties used in the source.
     */
    public static final String SOURCE_KAFKA_PROPERTIES_PREFIX = "source.kafka.props.";

    /**
     * The prefix for Kafka properties used in the sink.
     */
    public static final String SINK_KAFKA_PROPERTIES_PREFIX = "sink.kafka.props.";


    public static final ConfigOption<String> SOURCE_KAFKA_TOPIC =
            ConfigOptions.key("source.kafka.topic")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("The Kafka topic used in the source.");


    public static final ConfigOption<String> SINK_KAFKA_TOPIC =
            ConfigOptions.key("sink.kafka.topic")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("The Kafka topic used in the sink.");


    public static final ConfigOption<Integer> COUNT_WINDOW_TIME =
            ConfigOptions.key("count.window.time")
                    .intType()
                    .defaultValue(5)
                    .withDescription("The aggregate window time");


    public static final ConfigOption<Integer> WARTERMARK_MAX_ORDERNESS =
            ConfigOptions.key("watermark.max.orderness")
                    .intType()
                    .defaultValue(5)
                    .withDescription("The aggregate watermark max time");


    public static final ConfigOption<String> STARTUP_MODE =
            ConfigOptions.key("startup.mode")
                    .stringType()
                    .defaultValue("group")
                    .withDescription("The offset commit mode for the consumer.");


    public static final ConfigOption<Boolean> LOG_FAILURES_ONLY =
            ConfigOptions.key("log.failures.only")
                    .booleanType()
                    .defaultValue(false)
                    .withDescription("Defines whether the producer should fail on errors, or only log them.");


    public static final ConfigOption<String> MEASUREMENT_NAME =
            ConfigOptions.key("measurement.name")
                    .stringType()
                    .defaultValue("application_protocol_stat")
                    .withDescription("The data identification.");
}