package dk.rosenlund.sensorplatform.camel.routes;

import com.pi4j.component.temperature.TemperatureSensor;
import dk.rosenlund.sensorplatform.camel.sensors.TemperatureService;
import dk.rosenlund.sensorplatform.model.SensorOutput;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CamelRouteBuilderTest {

    String contextPath = "camel";

    @MockBean
    TemperatureService temperatureServiceMock;

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

        given(this.temperatureServiceMock.getSensorOutputList())
                .willReturn(sensorOutput);
    }

    @Test
    public void testRestAPI_getAllSensors() throws JSONException {
        ResponseEntity<String> response =
                restTemplate.getForEntity(String.format("/%s/sensors/", contextPath), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONArray jsonResponse = new JSONArray(response.getBody());
        JSONObject firstObject = jsonResponse.getJSONObject(0);
        JSONObject secondObject = jsonResponse.getJSONObject(1);

        assertEquals(TEMPERATURE_TEST_SENSOR_ONE_TYPE, firstObject.getString("type"));
        assertEquals(TEMPERATURE_TEST_SENSOR_ONE_NAME, firstObject.getString("name"));
        assertEquals(TEMPERATURE_TEST_SENSOR_ONE_VALUE, firstObject.getJSONArray("values").getDouble(0), 0.001);

        assertEquals(TEMPERATURE_TEST_SENSOR_TWO_TYPE, secondObject.getString("type"));
        assertEquals(TEMPERATURE_TEST_SENSOR_TWO_NAME, secondObject.getString("name"));
        assertEquals(TEMPERATURE_TEST_SENSOR_TWO_VALUE, secondObject.getJSONArray("values").getDouble(0), 0.001);
    }

    @Test
    public void testRestAPI_getAllSensorForType() throws JSONException {
        ResponseEntity<String> response =
                restTemplate.getForEntity(String.format("/%s/sensors/%s", contextPath, TEMPERATURE_TEST_SENSOR_ONE_TYPE), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONArray jsonResponse = new JSONArray(response.getBody());
        JSONObject jsonObject = jsonResponse.getJSONObject(0);

        assertEquals(TEMPERATURE_TEST_SENSOR_ONE_NAME, jsonObject.getString("name"));
        assertEquals(TEMPERATURE_TEST_SENSOR_ONE_TYPE, jsonObject.getString("type"));
        assertEquals(TEMPERATURE_TEST_SENSOR_ONE_VALUE, jsonObject.getJSONArray("values").getDouble(0), 0.001);
    }

    @Test
    public void testRestAPI_getSensorForTypeAndName() throws JSONException {
        ResponseEntity<String> response =
                restTemplate.getForEntity(String.format("/%s/sensors/%s/%s", contextPath, TEMPERATURE_TEST_SENSOR_ONE_TYPE, TEMPERATURE_TEST_SENSOR_ONE_NAME), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONArray jsonResponse = new JSONArray(response.getBody());
        JSONObject jsonObject = jsonResponse.getJSONObject(0);

        assertEquals(TEMPERATURE_TEST_SENSOR_ONE_NAME, jsonObject.getString("name"));
        assertEquals(TEMPERATURE_TEST_SENSOR_ONE_TYPE, jsonObject.getString("type"));
        assertEquals(TEMPERATURE_TEST_SENSOR_ONE_VALUE, jsonObject.getJSONArray("values").getDouble(0), 0.001);
    }

}