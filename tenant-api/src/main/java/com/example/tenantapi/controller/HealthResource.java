package com.example.tenantapi.controller;

import com.example.tenantapi.service.HealthService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthResource {

    private HealthService service;

    @Inject
    public HealthResource(HealthService service) {
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
        return Response
                .status(Response.Status.OK)
                .build();
    }
}
