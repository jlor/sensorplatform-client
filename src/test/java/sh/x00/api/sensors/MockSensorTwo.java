package sh.x00.api.sensors;


import sh.x00.api.model.SensorOutput;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Alternative
@ApplicationScoped
public class MockSensorTwo implements Sensor {
    String type = "MockSensorTwo";
    List<Double> values = Collections.emptyList();

    @Override
    public String getSensorType() {
        return type;
    }

    @Override
    public SensorOutput getSensorOutput(String sensorName) {
        return new SensorOutput(type, sensorName, values);
    }

    @Override
    public List<SensorOutput> getSensorOutputList() {
        return Arrays.asList(new SensorOutput(type, "sensorOne", values),
                new SensorOutput(type, "sensorTwo", values));
    }
}