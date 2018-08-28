package dk.rosenlund.sensorplatform.camel.sensors;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.temperature.TemperatureScale;
import dk.rosenlund.sensorplatform.model.SensorOutput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TemperatureServiceTest {

    @Test
    public void getSensorOutputList_NoSensors() {
        // Arrange
        List<TemperatureSensor> sensors = Collections.emptyList();
        TemperatureService temperatureService = mock(TemperatureService.class);
        when(temperatureService.getSensors()).thenReturn(sensors);
        when(temperatureService.getSensorOutputList()).thenCallRealMethod();
        // Act
        List<SensorOutput> sensorOutputList = temperatureService.getSensorOutputList();
        // Assert
        assertEquals(0, sensorOutputList.size());
    }

    @Test
    public void getSensorOutputList_WithSensor() {
        // Arrange
        TemperatureSensor temperatureSensor = mock(TemperatureSensor.class);
        when(temperatureSensor.getTemperature(TemperatureScale.CELSIUS)).thenReturn(123d);
        when(temperatureSensor.getName()).thenReturn("name");

        List<TemperatureSensor> sensors = Arrays.asList(temperatureSensor);
        TemperatureService temperatureService = mock(TemperatureService.class);
        when(temperatureService.getSensors()).thenReturn(sensors);
        when(temperatureService.getSensorOutputList()).thenCallRealMethod();
        // Act
        List<SensorOutput> sensorOutputList = temperatureService.getSensorOutputList();
        // Assert
        assertEquals(1, sensorOutputList.size());
        assertEquals("name", sensorOutputList.get(0).getSensorName());
        assertEquals(123d, sensorOutputList.get(0).getValues().get(0), 0.1);
    }
}