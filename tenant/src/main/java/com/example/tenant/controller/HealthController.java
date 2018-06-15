package com.example.tenant.controller;

import com.example.tenant.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Component
@Path("/api/v1/health")
@Produces("application/json")
public class HealthController {

    private HealthService service;

    @Autowired
    public HealthController(HealthService service) {
        this.service = service;
    }

    @GET
    @Path("/check")
    public Response check() {
        Response.Status status = Response.Status.OK;
        if (!service.isOk()) {
            status = Response.Status.SERVICE_UNAVAILABLE;
        }

        return Response.status(status).entity(service.getHealth()).build();
    }

    @GET
    @Path("/ping")
    public Response pong() {
        return Response.status(Response.Status.OK).build();
    }
}
