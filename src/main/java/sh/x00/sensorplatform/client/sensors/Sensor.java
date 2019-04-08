package sh.x00.sensorplatform.client.sensors;

import sh.x00.sensorplatform.client.model.SensorOutput;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public interface Sensor {
    String getSensorType();
    SensorOutput getSensorOutput(String sensorName);
    List<SensorOutput> getSensorOutputList();
}
