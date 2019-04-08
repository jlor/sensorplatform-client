package sh.x00.sensorplatform.client;

import sh.x00.sensorplatform.client.model.SensorOutput;
import sh.x00.sensorplatform.client.service.SensorStatusService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestApi {

    @Inject
    SensorStatusService sensorStatusService;

    @GET
    @Path("/test/{sensorType}/{sensorName}/{sensorValue}")
    public Response getSensorTest(@PathParam("sensorType") String sensorType,
                                      @PathParam("sensorName") String sensorName,
                                      @PathParam("sensorValue") Double sensorValue) {
        return getResponse(new SensorOutput(sensorType, sensorName, sensorValue));
    }

    @GET
    @Path("/sensors")
    public Response getSensors() {
        List<SensorOutput> sensorStatus = sensorStatusService.getSensorStatus();
        return getResponse(sensorStatus);
    }

    @GET
    @Path("/sensor/{sensorType}")
    public Response getSensorByType(@PathParam("sensorType") String sensorType) {
        List<SensorOutput> sensorOutputList = sensorStatusService.getSensorOutputList(sensorType);
        return getResponse(sensorOutputList);
    }

    @GET
    @Path("/sensor/{sensorType}/{sensorName}")
    public Response getSensorByTypeAndName(@PathParam("sensorType") String sensorType, @PathParam("sensorName") String sensorName) {
        SensorOutput specificSensorOutput = sensorStatusService.getSpecificSensorOutput(sensorType, sensorName);
        return getResponse(specificSensorOutput);
    }

    Response getResponse(Object obj) {
        if (obj != null) return Response.ok(obj).build();
        return Response.noContent().build();
    }
}