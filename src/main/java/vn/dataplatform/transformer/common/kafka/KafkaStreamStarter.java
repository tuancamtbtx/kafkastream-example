package vn.dataplatform.transformer.common.kafka;

import com.google.inject.Inject;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.dataplatform.transformer.constants.MainConfig;
import vn.dataplatform.transformer.entity.config.KafkaStreamConfig;

import java.time.Duration;
import java.util.Properties;

public class KafkaStreamStarter {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaStreamStarter.class.getName());

    private final KafkaStreamConfig kafkaStreamConfig;
    private final Topology topology;

    @Inject
    public KafkaStreamStarter(KafkaStreamConfig kafkaStreamConfig, Topology topology) {
        this.topology = topology;
        this.kafkaStreamConfig = kafkaStreamConfig;
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.CLIENT_ID_CONFIG, MainConfig.KAFKA_CLIENT_ID_CONFIG);
        props.put("group.id", "test-group");
        props.put("auto.offset.reset", "earliest");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, MainConfig.KAFKA_APPLICATION_ID_CONFIG);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, MainConfig.KAFKA_BROKER);
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 1);
        props.put(StreamsConfig.STATE_DIR_CONFIG, "./tmp");
        return props;
    }

    public static KafkaStreamConfig getT() {
        KafkaStreamConfig test = new KafkaStreamConfig();
        test.setJobName("abc");
        test.setApplicationId("test1");
        test.setStreamProperties(getProperties());
        return test;
    }

    /**
     * Start kafka stream with mocked stream config and topology
     */
    public void start() {
        KafkaStreamConfig kafkaStreamConfiga = getT();
        LOGGER.info("test: {}", kafkaStreamConfiga.getStreamProperties());
        LOGGER.info("Start kafka stream {} with id {}", kafkaStreamConfiga.getJobName(),
                kafkaStreamConfiga.getApplicationId());
        LOGGER.info("Kafka topology {}", topology.describe());
        KafkaStreams kafkaStreams = new KafkaStreams(topology, kafkaStreamConfiga.getStreamProperties());
        kafkaStreams.setUncaughtExceptionHandler((exception) -> StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse.REPLACE_THREAD);
        kafkaStreams.setStateListener((newState, oldState) -> {
            if (oldState == KafkaStreams.State.REBALANCING && newState == KafkaStreams.State.RUNNING) {
                LOGGER.info("Readiness : RUNNING");
            } else {
                LOGGER.info("Readiness: FAILED");
            }
        });
        kafkaStreams.cleanUp();
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread("shutdown-hook") {
            @Override
            public void run() {
                LOGGER.info("Stop streaming");
                kafkaStreams.close(Duration.ofSeconds(30));
            }
        });
    }
}
