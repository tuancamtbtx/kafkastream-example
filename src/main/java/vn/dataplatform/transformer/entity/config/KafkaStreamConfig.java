package vn.dataplatform.transformer.entity.config;

import lombok.Data;

import java.util.Properties;

@Data
public class KafkaStreamConfig {
    String jobName;
    String applicationId;
    Properties streamProperties;
}
