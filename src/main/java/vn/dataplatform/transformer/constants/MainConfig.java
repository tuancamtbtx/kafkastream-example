package vn.dataplatform.transformer.constants;

import vn.dataplatform.transformer.common.utils.XEnvUtils;

public class MainConfig {
    public static final String KAFKA_BROKER = XEnvUtils.getEnvironmentEntry("KAFKA_BROKER");
    public static final String KAFKA_CLIENT_ID_CONFIG = XEnvUtils.getEnvironmentEntry("KAFKA_CLIENT_ID_CONFIG");
    public static final String KAFKA_APPLICATION_ID_CONFIG = XEnvUtils.getEnvironmentEntry("KAFKA_APPLICATION_ID_CONFIG");
    public static final String KAFKA_TOPIC_AQI_LOG = XEnvUtils.getEnvironmentEntry("KAFKA_TOPIC_AQI_LOG");

    public static final String DRUID_HOST = XEnvUtils.getEnvironmentEntry("DRUID_HOST");
    public static final String DRUID_PORT = XEnvUtils.getEnvironmentEntry("DRUID_PORT");

    public static final String NUM_STREAM_THREADS_CONFIG = "num.stream.threads";
    public static final String KAFKA_GROUP = XEnvUtils.getEnvironmentEntry("KAFKA_GROUP");
    public static final int HTTP_PORT = 9090;
    public static final int NUM_THREAD_KAFKA = 1;
}

