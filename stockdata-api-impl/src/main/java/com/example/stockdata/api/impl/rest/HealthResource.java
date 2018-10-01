package com.example.stockdata.api.impl.rest;

import com.example.stockdata.api.spec.model.HealthModel;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/v1/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthResource {

    @GET
    @Path("/check")
    public HealthModel check() {
        HealthModel model = new HealthModel();
        model.setHello("Yo");
        return model;
    }

    @GET
    @Path("/ping")
    public Response pong() {
        return Response
                .status(Response.Status.OK)
                .build();
    }
}
