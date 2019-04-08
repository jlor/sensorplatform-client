package sh.x00.api.service;

import sh.x00.api.model.SensorOutput;
import sh.x00.api.sensors.Sensor;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SensorStatusService {
    @Inject @Any
    Instance<Sensor> sensors;

    public List<SensorOutput> getSensorStatus() {
        return getSensorOutputList(null);
    }

    public SensorOutput getSpecificSensorOutput(@NotNull String sensorType, @NotNull String sensorName) {
        return sensors.stream()
                .filter(sensor -> sensorType.equalsIgnoreCase(sensor.getSensorType()))
                .map(sensor -> sensor.getSensorOutput(sensorName))
                .findFirst()
                .orElse(null);
    }

    public List<SensorOutput> getSensorOutputList(@Null String sensorType) {
        return sensors.stream()
                .filter(sensor -> sensorType == null || sensorType.equalsIgnoreCase(sensor.getSensorType()))
                .map(Sensor::getSensorOutputList)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
