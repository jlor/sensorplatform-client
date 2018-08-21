package dk.rosenlund.sensorplatform.camel.routes;

import dk.rosenlund.sensorplatform.camel.service.SensorService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class CamelRouteBuilder extends RouteBuilder {

    private static final String REST_ALL_SENSORS_REQUEST_URI = "direct:restAllSensor";
    private static final String REST_SENSOR_TYPE_REQUEST_URI = "direct:restSensorType";
    private static final String REST_SPECIFIC_SENSOR_REQUEST_URI = "direct:restSpecificSensor";

    private static final String SENSOR_TRIGGER_ROUTE_URI = "direct:sensorTrigger";
    private static final String SENSOR_OUTPUT_ROUTE_URI = "stream:out";

    private static final String SENSOR_TRIGGER_ROUTE_ID = "sensorTriggerId";
    private static final String SENSOR_OUTPUT_ROUTE_ID = "sensorOutputId";

    @Override
    public void configure() {
        getContext().setUseMDCLogging(true);
        getContext().setUseBreadcrumb(true);

        setupRest();
        setupTimer();
        setupTriggerSubscribers();
    }

    private void setupRest() {
        restConfiguration()
                .contextPath("/sensors")
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Sensor Platform client REST API")
                .apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json);

        setupRestEndpoints();
    }

    private void setupRestEndpoints() {
        rest("/rest/request/")
                .get("/alive")
                .route()
                .setBody(constant("OK"))
                .endRest()
                .post()
                .to("direct:handleAliveRequest");

        from("direct:handleAliveRequest").log("Received request: ${body}");
        //todo: implement me

        rest("/rest/")
                .get("sensors").to(REST_ALL_SENSORS_REQUEST_URI)
                .get("sensors/{sensorType}").to(REST_SENSOR_TYPE_REQUEST_URI)
                .get("sensors/{sensorType}/{sensorName}").to(REST_SPECIFIC_SENSOR_REQUEST_URI);

        from(REST_ALL_SENSORS_REQUEST_URI)
                .bean(SensorService.class, "fetchSensorStatus(${exchange})");
        from(REST_SENSOR_TYPE_REQUEST_URI)
                .bean(SensorService.class, "fetchSensorType(${header.sensorType}, ${exchange})");
        from(REST_SPECIFIC_SENSOR_REQUEST_URI)
                .bean(SensorService.class, "fetchSpecificSensor(${header.sensorType}, ${header.sensorName}, ${exchange})");
    }

    private void setupTimer() {
        from("timer:sensortrigger?period={{timer.period}}")
                .routeId(SENSOR_TRIGGER_ROUTE_ID)
                .log("Sensor status triggered")
                .to(SENSOR_TRIGGER_ROUTE_URI);
    }

    private void setupTriggerSubscribers() {
        from(SENSOR_TRIGGER_ROUTE_URI)
                .routeId(SENSOR_OUTPUT_ROUTE_ID)
                .bean(SensorService.class, "fetchSensorStatus(${exchange})")
                .to(SENSOR_OUTPUT_ROUTE_URI);
    }

}
