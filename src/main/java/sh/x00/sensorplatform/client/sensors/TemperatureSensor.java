package sh.x00.sensorplatform.client.sensors;

import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;
import sh.x00.sensorplatform.client.model.SensorOutput;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Alternative
@ApplicationScoped
public class TemperatureSensor implements Sensor {

    String sensorType = "TemperatureSensor";

    @Override
    public String getSensorType() {
        return sensorType;
    }

    @Override
    public SensorOutput getSensorOutput(String sensorName) {
        Optional<com.pi4j.component.temperature.TemperatureSensor> temperatureSensor = getSensors().stream()
                .filter(sensor -> sensor.getName().equalsIgnoreCase(sensorName))
                .findFirst();

        return temperatureSensor.map(this::getSensorOutput).orElse(null);
    }

    @Override
    public List<SensorOutput> getSensorOutputList() {
        List<SensorOutput> sensorMessages = new ArrayList<>();

        List<com.pi4j.component.temperature.TemperatureSensor> temperatureSensors = getSensors();
        if (!temperatureSensors.isEmpty()) {
            temperatureSensors.forEach(sensor -> sensorMessages.add(getSensorOutput(sensor)));
        }

        return sensorMessages;
    }

    protected SensorOutput getSensorOutput(com.pi4j.component.temperature.TemperatureSensor sensor) {
        return new SensorOutput(
                getSensorType(),
                sensor.getName(),
                sensor.getTemperature(TemperatureScale.CELSIUS)
        );
    }

    protected List<com.pi4j.component.temperature.TemperatureSensor> getSensors() {
        W1Master w1Master = new W1Master();
        return w1Master.getDevices(com.pi4j.component.temperature.TemperatureSensor.class);
    }
}
