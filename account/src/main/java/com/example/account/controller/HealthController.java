package com.example.account.controller;

import com.example.account.service.HealthService;
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
    @Path("/ping")
    public Response pong() {
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/check")
    public Response checkFirstRecord() {
        Response.Status status = Response.Status.OK;

        if (!service.isOk()) {
            status = Response.Status.SERVICE_UNAVAILABLE;
        }

        return Response.status(status).entity(service.getHealth()).build();
    }
}
