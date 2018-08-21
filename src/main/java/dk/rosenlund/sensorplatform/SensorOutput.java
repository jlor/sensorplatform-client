package dk.rosenlund.sensorplatform;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorOutput {

    @JsonProperty("SensorName")
    private String sensorName;

    @JsonProperty("Values")
    private List<Double> values;

    public SensorOutput(String sensorName, List<Double> values) {
        this.sensorName = sensorName;
        this.values = values;
    }

    public SensorOutput(String sensorName, Double value) {
        this(sensorName, Collections.singletonList(value));
    }
}
