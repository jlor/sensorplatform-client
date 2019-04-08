package sh.x00.api.sensors;

import sh.x00.api.model.SensorOutput;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public interface Sensor {
    String getSensorType();
    SensorOutput getSensorOutput(String sensorName);
    List<SensorOutput> getSensorOutputList();
}
