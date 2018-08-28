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

        W1Master w1master = new W1Master();
        // 1wire TemperatureSensors
        List<TemperatureSensor> temperatureSensors = w1master.getDevices(TemperatureSensor.class);
        if (temperatureSensors.size() > 0) {
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
}
