package vn.dataplatform.transformer.topology.transform;

import com.google.gson.Gson;
import org.apache.kafka.streams.kstream.ValueMapperWithKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.dataplatform.transformer.entity.log.StationLogEntity;

public class StationLogMapper implements ValueMapperWithKey<String, String, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationLogMapper.class.getName());
    @Override
    public String apply(String key, String value) {
//        StationLogEntity
        Gson gson = new Gson();
        StationLogEntity log = gson.fromJson(value, StationLogEntity.class);
        LOGGER.info("value: {}", log);
        return value;
    }
}
