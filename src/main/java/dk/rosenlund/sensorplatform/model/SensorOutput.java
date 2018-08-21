package dk.rosenlund.sensorplatform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorOutput {

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("type")
    private String sensorType;

    @JsonProperty("name")
    private String sensorName;

    @JsonProperty("values")
    private List<Double> values;

    public SensorOutput(String sensorType, String sensorName, List<Double> values) {
        this.sensorType = sensorType;
        this.sensorName = sensorName;
        this.values = values;
        this.timestamp = System.currentTimeMillis();
    }

    public SensorOutput(String sensorType, String sensorName, Double value) {
        this(sensorType, sensorName, Collections.singletonList(value));
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getSensorType() {
        return sensorType;
    }

    public String getSensorName() {
        return sensorName;
    }

    public List<Double> getValues() {
        return values;
    }
}
