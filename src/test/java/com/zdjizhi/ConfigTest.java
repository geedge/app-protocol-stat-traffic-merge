package com.zdjizhi;

import com.zdjizhi.conf.FusionConfiguration;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import static com.zdjizhi.conf.FusionConfigs.*;

public class ConfigTest {
    public static void main(String[] args) {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        final ParameterTool tool;
        try {
            tool = ParameterTool.fromPropertiesFile("D:\\workerspace\\flink\\test.properties");
            final Configuration config = tool.getConfiguration();
            environment.getConfig().setGlobalJobParameters(config);
            final FusionConfiguration fusionConfiguration = new FusionConfiguration(config);

            System.out.println(config.get(SOURCE_KAFKA_TOPIC));
            System.out.println(config.get(SINK_KAFKA_TOPIC));
            System.out.println(fusionConfiguration.getProperties(SOURCE_KAFKA_PROPERTIES_PREFIX));
            System.out.println(fusionConfiguration.getProperties(SINK_KAFKA_PROPERTIES_PREFIX));


            final FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>(
                    config.get(SOURCE_KAFKA_TOPIC),
                    new SimpleStringSchema(),
                    fusionConfiguration
                            .getProperties(SOURCE_KAFKA_PROPERTIES_PREFIX));

            final DataStreamSource<String> sourceStream = environment.addSource(kafkaConsumer);

            sourceStream.process(new ProcessFunction<String, String>() {

                @Override
                public void processElement(String value, ProcessFunction<String, String>.Context ctx, Collector<String> out) {

                    out.collect(value);
                }
            }).print();



            environment.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
