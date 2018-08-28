package dk.rosenlund.sensorplatform.camel.sensors;

import dk.rosenlund.sensorplatform.model.SensorOutput;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SensorService {
    List<SensorOutput> getSensorOutputList();
}
