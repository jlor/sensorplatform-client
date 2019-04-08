package sh.x00.sensorplatform.client.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RegisterForReflection
public class SensorOutput {
    Long timestamp;
    String sensorType;
    String sensorName;
    List<Double> values;
    public SensorOutput() {
        timestamp = System.currentTimeMillis();
    }

    public SensorOutput(String sensorType, String sensorName, Double value) {
        this(sensorType, sensorName, Collections.singletonList(value));
    }

    public SensorOutput(String sensorType, String sensorName, List<Double> values) {
        this();
        this.sensorType = sensorType;
        this.sensorName = sensorName;
        this.values = values;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
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

    @Override
    public String toString() {
        return "SensorOutput{" +
                "timestamp=" + getTimestamp() +
                ", sensorType='" + getSensorType() + '\'' +
                ", sensorName='" + getSensorName() + '\'' +
                ", values=" + getValues() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorOutput that = (SensorOutput) o;
        return getTimestamp().equals(that.getTimestamp()) &&
                getSensorType().equals(that.getSensorType()) &&
                getSensorName().equals(that.getSensorName()) &&
                getValues().equals(that.getValues());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimestamp(), getSensorType(), getSensorName(), getValues());
    }
}
