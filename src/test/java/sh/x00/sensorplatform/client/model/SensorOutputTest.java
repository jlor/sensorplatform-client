package sh.x00.sensorplatform.client.model;

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

    @Test
    public void testSensorOutput_sameObject() {
        // Arrange
        SensorOutput sensorOutput = new SensorOutput("type", "name", 123d);
        // Act
        boolean equals = sensorOutput.equals(sensorOutput);
        // Assert
        assertTrue(equals);
    }

    @Test
    public void testSensorOutput_nullObject() {
        // Arrange
        SensorOutput sensorOutput = new SensorOutput("type", "name", 123d);
        // Act
        boolean equals = sensorOutput.equals(null);
        boolean sensorOneEqualsString = sensorOutput.equals("not a sensor");
        // Assert
        assertFalse(equals);
        assertFalse(sensorOneEqualsString);
    }

    @Test
    public void testSensorOutput_differentObject() {
        // Arrange
        SensorOutput sensorOutputOne = new SensorOutput("type", "name", 123d);
        SensorOutput sensorOutputTwo = new SensorOutput("type", "name", 123d);
        sensorOutputTwo.setTimestamp(sensorOutputOne.getTimestamp() + 1000L);
        SensorOutput differentName = new SensorOutput("type", "eman", 123d);
        differentName.setTimestamp(sensorOutputOne.getTimestamp());
        SensorOutput differentType = new SensorOutput("epyt", "name", 123d);
        differentType.setTimestamp(sensorOutputOne.getTimestamp());
        SensorOutput differentValues = new SensorOutput("type", "name", Arrays.asList(123d, 321d));
        differentValues.setTimestamp(sensorOutputOne.getTimestamp());
        // Act
        boolean sensorTimestampDifferent = sensorOutputOne.equals(sensorOutputTwo);
        boolean sensorTypeDifferent = sensorOutputOne.equals(differentType);
        boolean sensorNameDifferent = sensorOutputOne.equals(differentName);
        boolean sensorValuesDifferent = sensorOutputOne.equals(differentValues);
        // Assert
        assertFalse(sensorTimestampDifferent);
        assertFalse(sensorTypeDifferent);
        assertFalse(sensorNameDifferent);
        assertFalse(sensorValuesDifferent);
    }
}