package dk.rosenlund.sensorplatform.camel.sensors;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;
import dk.rosenlund.sensorplatform.model.SensorOutput;

import java.util.ArrayList;
import java.util.List;

public class TemperatureService implements SensorService {
    @Override
    public List<SensorOutput> getSensorOutputList() {
        List<SensorOutput> sensorMessages = new ArrayList<>();

        List<TemperatureSensor> temperatureSensors = getSensors();
        if (!temperatureSensors.isEmpty()) {
            temperatureSensors.forEach(s -> sensorMessages.add(
                    new SensorOutput(
                            TemperatureSensor.class.getName(),
                            s.getName(),
                            s.getTemperature(TemperatureScale.CELSIUS)
                    )
            ));
        }

        return sensorMessages;
    }

    protected List<TemperatureSensor> getSensors() {
        // 1wire TemperatureSensors
        W1Master w1Master = new W1Master();
        return w1Master.getDevices(TemperatureSensor.class);
    }
}
