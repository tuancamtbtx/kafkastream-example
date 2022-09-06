package vn.dataplatform.transformer.entity.config;

import lombok.Data;

@Data
public class DruidIndexConfig {
    int bulkSize;
    int limitIndexingRate;

}
