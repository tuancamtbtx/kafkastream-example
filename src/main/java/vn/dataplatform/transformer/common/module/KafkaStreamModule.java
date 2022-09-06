package vn.dataplatform.transformer.common.module;

import com.google.inject.AbstractModule;

public class KafkaStreamModule extends AbstractModule {

    @Override
    protected void configure() {
        //for future kafka configuration
    }

//    @Provides
//    @Singleton
//    KafkaStreamConfig kafkaStreamConfig(StreamJobConfig streamJobConfig) {
//        KafkaStreamConfig streamConfig = new KafkaStreamConfig();
//        streamConfig.setJobName(streamJobConfig.getJobName());
//        streamConfig.setStreamProperties(getProperties());
//        streamConfig.setApplicationId(getProperties().getProperty(StreamsConfig.APPLICATION_ID_CONFIG));
//        return streamConfig;
//    }

//    @Provides
//    StreamJobConfig streamJobConfig() {
//        return new StreamJobConfig("Ilotusland Bulk Insert Druid");
//    }
//    @Provides
//    private static Properties getProperties() {
//        Properties props = new Properties();
//        props.put(StreamsConfig.CLIENT_ID_CONFIG, MainConfig.KAFKA_CLIENT_ID_CONFIG);
//        props.put("group.id", MainConfig.KAFKA_GROUP);
//        props.put("auto.offset.reset", "earliest");
//        props.put(StreamsConfig.APPLICATION_ID_CONFIG,"MainConfig.KAFKA_APPLICATION_ID_CONFIG");
//        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, MainConfig.KAFKA_BROKER);
//        props.put(StreamsConfig.STATE_DIR_CONFIG, "./tmp");
//        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, MainConfig.NUM_THREAD_KAFKA);
//        return props;
//    }

}
