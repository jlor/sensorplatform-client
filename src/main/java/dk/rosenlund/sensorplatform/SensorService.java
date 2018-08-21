package dk.rosenlund.sensorplatform;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SensorService {

    public void fetchSensorStatus(Exchange exchange) {
        final List<SensorOutput> sensorMessages = new ArrayList<>();
        W1Master w1master = new W1Master();
        for (TemperatureSensor device : w1master.getDevices(TemperatureSensor.class)) {
            sensorMessages.add(new SensorOutput(device.getName(), device.getTemperature(TemperatureScale.CELSIUS)));
        }

        exchange.getIn().setBody(sensorMessages);
    }
}
