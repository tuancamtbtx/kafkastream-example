package vn.dataplatform.transformer.entity.log;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StationLogEntity {
    String measureName;
    float value;
    String unit;
    String time;
    int statusDevice;
    String organizationId;
    String organizationName;
    String stationName;
    String stationId;
    String fileName;
    LocationEntity stationLocation;
}
