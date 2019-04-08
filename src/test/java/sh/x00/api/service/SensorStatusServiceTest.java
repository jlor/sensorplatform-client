package sh.x00.api.service;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import sh.x00.api.model.SensorOutput;

import javax.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class SensorStatusServiceTest {

    @Inject
    SensorStatusService sensorStatusService;

    @Test
    public void testGetSensorStatus_allSensors() {
        // Arrange

        // Act
        List<SensorOutput> sensorOutputList = sensorStatusService.getSensorStatus();

        // Assert
        assertEquals(4, sensorOutputList.size());
    }

    @Test
    public void testGetSensorStatus_specificSensorTypeAndName() {
        // Arrange

        // Act
        SensorOutput specificSensorOutput = sensorStatusService.getSpecificSensorOutput("MockSensorOne", "sensorOne");

        // Assert
        assertEquals("sensorOne", specificSensorOutput.getSensorName());
        assertEquals("MockSensorOne", specificSensorOutput.getSensorType());
        assertEquals(2, specificSensorOutput.getValues().size());
    }

    @Test
    public void testGetSensorStatus_specificSensorType() {
        // Arrange

        // Act
        List<SensorOutput> sensorOutputList = sensorStatusService.getSensorOutputList("MockSensorOne");

        // Assert
        assertEquals(2, sensorOutputList.size());
        assertEquals("sensorOne", sensorOutputList.get(0).getSensorName());
        assertEquals("sensorTwo", sensorOutputList.get(1).getSensorName());
    }
}