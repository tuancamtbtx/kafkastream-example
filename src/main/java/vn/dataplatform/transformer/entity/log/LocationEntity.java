package vn.dataplatform.transformer.entity.log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonAutoDetect
public class LocationEntity implements Serializable {
    @SerializedName("long")
    @JsonProperty("long")
    double lon;
    @SerializedName("lat")
    @JsonProperty("lat")
    double lat;
}
