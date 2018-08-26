package dk.rosenlund.sensorplatform.camel.routes;

import com.pi4j.component.temperature.TemperatureSensor;
import dk.rosenlund.sensorplatform.camel.service.SensorService;
import dk.rosenlund.sensorplatform.model.SensorOutput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CamelRouteBuilderTest {

    String contextPath = "camel";

    @MockBean
    SensorService sensorServiceMock;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String TEMPERATURE_TEST_SENSOR_ONE_TYPE = TemperatureSensor.class.getName();
    private static final String TEMPERATURE_TEST_SENSOR_ONE_NAME = "w1temperatureSensorOne";
    private static final double TEMPERATURE_TEST_SENSOR_ONE_VALUE = 12.34;
    private static final String TEMPERATURE_TEST_SENSOR_TWO_TYPE = TemperatureSensor.class.getName();
    private static final String TEMPERATURE_TEST_SENSOR_TWO_NAME = "w1temperatureSensorTwo";
    private static final double TEMPERATURE_TEST_SENSOR_TWO_VALUE = 56.78;

    @Before
    public void setup() {
        List<SensorOutput> sensorOutput = Arrays.asList(new SensorOutput(TEMPERATURE_TEST_SENSOR_ONE_TYPE,
                                                                        TEMPERATURE_TEST_SENSOR_ONE_NAME,
                                                                        TEMPERATURE_TEST_SENSOR_ONE_VALUE),
                                                        new SensorOutput(TEMPERATURE_TEST_SENSOR_TWO_TYPE,
                                                                        TEMPERATURE_TEST_SENSOR_TWO_NAME,
                                                                        TEMPERATURE_TEST_SENSOR_TWO_VALUE));
        //todo: Implement a way to use SpringRunner with Mockito functionality.
        Mockito.when(sensorServiceMock.getSensorOutputList()).thenReturn(sensorOutput);
    }

    @Test
    public void testRestAPI_getAllSensors() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(String.format("/%s/sensors/", contextPath), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //todo: Implement check of content
    }

    @Test
    public void testRestAPI_getAllSensorForType() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(String.format("/%s/sensors/%s", contextPath, TEMPERATURE_TEST_SENSOR_ONE_TYPE), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //todo: Implement check of content

    }

    @Test
    public void testRestAPI_getSensorForTypeAndName() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(String.format("/%s/sensors/%s/%s", contextPath, TEMPERATURE_TEST_SENSOR_ONE_TYPE, TEMPERATURE_TEST_SENSOR_ONE_NAME), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //todo: Implement check of content
    }

}