package sh.x00.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class RestApiTest {

    @Test
    public void testSensorEndpoint_testEndpoint() {
        given()
          .when().get("/api/test/type/name/11")
          .then()
             .statusCode(200)
             .body(containsString("{\"sensorName\":\"name\",\"sensorType\":\"type\","));
    }

    @Test
    public void testSensorEndpoint_noSensor() {
        given()
            .when().get("/api/sensor/nonexistent/sensor")
            .then()
                .statusCode(204);
    }

    @Test
    public void testSensorEndpoint_sensorTypeAndName() {
        given()
            .when().get("/api/sensor/mocksensorone/sensorone")
            .then()
                .statusCode(200)
                .body(containsString("{\"sensorName\":\"sensorone\",\"sensorType\":\"MockSensorOne\","));
    }

    @Test
    public void testSensorEndpoint_sensorType() {
        given()
                .when().get("/api/sensor/mocksensortwo")
                .then()
                .statusCode(200)
                    .body(containsString("[{\"sensorName\":\"sensorOne\",\"sensorType\":\"MockSensorTwo\","))
                .and()
                    .body(containsString("{\"sensorName\":\"sensorTwo\",\"sensorType\":\"MockSensorTwo\","));
    }

    @Test
    public void testSensorEndpoint_allSensors() {
        given()
                .when().get("/api/sensors")
                .then()
                .statusCode(200)
                    .body(containsString("{\"sensorName\":\"sensorOne\",\"sensorType\":\"MockSensorOne\","))
                .and()
                    .body(containsString("{\"sensorName\":\"sensorOne\",\"sensorType\":\"MockSensorTwo\","));
    }
}