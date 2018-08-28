package dk.rosenlund.sensorplatform.camel.service;

import dk.rosenlund.sensorplatform.camel.sensors.SensorService;
import dk.rosenlund.sensorplatform.model.SensorOutput;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SensorStatusComponent {

    @Autowired
    private List<SensorService> sensorServices;

    public void fetchSensorStatus(Exchange exchange) {
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
        return sensorServices.stream()
                .map(SensorService::getSensorOutputList)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
