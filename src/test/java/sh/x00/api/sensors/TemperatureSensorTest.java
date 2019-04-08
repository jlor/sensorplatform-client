package sh.x00.api.sensors;

import com.pi4j.temperature.TemperatureScale;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import sh.x00.api.model.SensorOutput;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TemperatureSensorTest {
    static TemperatureSensor sut = spy(new TemperatureSensor());

    @BeforeAll
    public static void setup() {
        List<com.pi4j.component.temperature.TemperatureSensor> sensors = new ArrayList<>();
        com.pi4j.component.temperature.TemperatureSensor sensorOne = mock(com.pi4j.component.temperature.TemperatureSensor.class);
        when(sensorOne.getTemperature(TemperatureScale.CELSIUS)).thenReturn(123d);
        when(sensorOne.getName()).thenReturn("sensorOne");
        sensors.add(sensorOne);

        com.pi4j.component.temperature.TemperatureSensor sensorTwo = mock(com.pi4j.component.temperature.TemperatureSensor.class);
        when(sensorTwo.getTemperature(TemperatureScale.CELSIUS)).thenReturn(-1d);
        when(sensorTwo.getName()).thenReturn("sensorTwo");
        sensors.add(sensorTwo);

        when(sut.getSensors()).thenReturn(sensors);
    }

    @Test
    public void testGetSensorOutput_forName() {
        // Arrange
        // Act
        SensorOutput sensorOne = sut.getSensorOutput("sensorOne");

        // Assert
        assertEquals("sensorOne", sensorOne.getSensorName());
        assertEquals(1, sensorOne.getValues().size());
        assertEquals(123d, sensorOne.getValues().get(0));
        assertEquals("TemperatureSensor", sensorOne.getSensorType());
    }

    @Test
    public void testGetSensorOutput_all() {
        // Arrange
        // Act
        List<SensorOutput> sensorOutputList = sut.getSensorOutputList();

        // Assert
        assertEquals(2, sensorOutputList.size());
        SensorOutput sensorOutput = sensorOutputList.get(1);
        assertEquals("sensorTwo", sensorOutput.getSensorName());
        assertEquals(-1d, sensorOutput.getValues().get(0));
        assertEquals("TemperatureSensor", sensorOutput.getSensorType());
    }

    @Test
    public void testGetSensorOutput_generator() {
        // Arrange
        com.pi4j.component.temperature.TemperatureSensor sensor = mock(com.pi4j.component.temperature.TemperatureSensor.class);
        when(sensor.getTemperature(TemperatureScale.CELSIUS)).thenReturn(123d);
        when(sensor.getName()).thenReturn("sensorOne");

        // Act
        SensorOutput sensorOutput = sut.getSensorOutput(sensor);

        // Assert
        assertEquals("sensorOne", sensorOutput.getSensorName());
        assertEquals(123d, sensorOutput.getValues().get(0));
        assertEquals(1, sensorOutput.getValues().size());
        assertEquals("TemperatureSensor", sensorOutput.getSensorType());
    }
}