package dk.rosenlund.sensorplatform;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MySpringBootRouter extends RouteBuilder {

    private static final String SENSOR_OUTPUT_ROUTE_URI = "stream:out";

    @Override
    public void configure() {
        getContext().setUseMDCLogging(true);
        getContext().setUseBreadcrumb(true);

        setupRest();
        setupTimer();
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
    }

    private void setupTimer() {
        from("timer:sensortrigger?period={{timer.period}}")
                .routeId("sensortrigger")
                .log("Sensor status triggered")
                .bean(SensorService.class, "fetchSensorStatus(${exchange})")
                .to(SENSOR_OUTPUT_ROUTE_URI);
    }

}
