package dk.rosenlund.sensorplatform.camel.service;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;
import dk.rosenlund.sensorplatform.model.SensorOutput;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SensorService {

    public void fetchSensorStatus(Exchange exchange) {
        //todo: Better abstraction of sensors
        exchange.getIn().setBody(getSensorOutputList());
    }

    public void fetchSpecificSensor(String sensorType, String sensorName, Exchange exchange) {
        List<SensorOutput> sensorMessages = getSensorOutputList();

        exchange.getIn().setBody(sensorMessages.stream()
                .filter(s -> sensorType.equalsIgnoreCase(s.getSensorType()) &&
                             sensorName.equalsIgnoreCase(s.getSensorName()))
                .collect(Collectors.toList()));
    }

    public void fetchSensorType(String sensorType, Exchange exchange) {
        List<SensorOutput> sensorMessages = getSensorOutputList();

        exchange.getIn().setBody(sensorMessages.stream()
                .filter(s -> sensorType.equalsIgnoreCase(s.getSensorType()))
                .collect(Collectors.toList()));
    }

    private List<SensorOutput> getSensorOutputList() {
        List<SensorOutput> sensorMessages = new ArrayList<>();

        W1Master w1master = new W1Master();
        // 1wire TemperatureSensors
        List<TemperatureSensor> temperatureSensors = w1master.getDevices(TemperatureSensor.class);
        if (temperatureSensors.size() > 0) {
            temperatureSensors.forEach(s -> sensorMessages.add(new SensorOutput(TemperatureSensor.class.getName(), s.getName(), s.getTemperature(TemperatureScale.CELSIUS))));
        }

        return sensorMessages;
    }
}
