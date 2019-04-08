package sh.x00.api.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SensorOutputTest {

    @Test
    public void testSensorOutput_singleValue() {
        // Arrange
        String type = "type";
        String name = "name";
        Double value = 123d;

        // Act
        SensorOutput sensorOutput = new SensorOutput(type, name, value);

        // Assert
        assertEquals(type, sensorOutput.getSensorType());
        assertEquals(name, sensorOutput.getSensorName());
        assertEquals(1, sensorOutput.getValues().size());
        assertEquals(value, sensorOutput.getValues().get(0));
    }

    @Test
    public void testSensorOutput_doubleValues() {
        // Arrange
        String type = "type";
        String name = "name";
        Double valueOne = 123d;
        Double valueTwo = 321d;

        // Act
        SensorOutput sensorOutput = new SensorOutput(type, name, Arrays.asList(valueOne, valueTwo));

        // Assert
        assertEquals(type, sensorOutput.getSensorType());
        assertEquals(name, sensorOutput.getSensorName());
        assertEquals(2, sensorOutput.getValues().size());
        assertEquals(valueOne, sensorOutput.getValues().get(0));
        assertEquals(valueTwo, sensorOutput.getValues().get(1));
    }

    @Test
    public void testSensorOutput_noValues() {
        // Arrange

        // Act
        SensorOutput sensorOutput = new SensorOutput();

        // Assert
        assertNull(sensorOutput.getSensorName());
        assertNull(sensorOutput.getSensorType());
        assertNull(sensorOutput.getValues());
        assertNotNull(sensorOutput.getTimestamp());
    }

    @Test
    public void testSensorOutput_toString() {
        // Arrange
        SensorOutput sensorOutput = new SensorOutput("type", "name", 123d);
        // Act
        String toString = sensorOutput.toString();
        // Assert
        assertTrue(toString.contains("SensorOutput{"));
        assertTrue(toString.contains("timestamp="+sensorOutput.getTimestamp()));
        assertTrue(toString.contains("sensorType='type'"));
        assertTrue(toString.contains("sensorName='name'"));
        assertTrue(toString.contains("values=[123.0]"));
    }

    @Test
    public void testSensorOutput_hashCode() {
        // Arrange
        SensorOutput sensorOutput = new SensorOutput("type", "name", 123d);
        // Act
        int firstInvocation = sensorOutput.hashCode();
        // Assert
        assertEquals(sensorOutput.hashCode(), firstInvocation);
    }

    @Test
    public void testSensorOutput_equals() {
        // Arrange
        SensorOutput sensorOutputOne = new SensorOutput("type", "name", 123d);
        SensorOutput sensorOutputTwo = new SensorOutput("type", "name", 123d);
        sensorOutputTwo.setTimestamp(sensorOutputOne.getTimestamp());
        // Act
        boolean equals = sensorOutputOne.equals(sensorOutputTwo);
        // Assert
        assertTrue(equals);
    }
}