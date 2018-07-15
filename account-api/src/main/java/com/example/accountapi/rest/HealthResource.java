package com.example.accountapi.rest;

import com.example.accountapi.service.HealthService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("/v1/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthResource {

    @Inject
    private HealthService service;

    @GET
    @Path("/check")
    public Response check() {
        Response.Status status = Response.Status.OK;

        if (!service.isOk()) {
            status = Response.Status.SERVICE_UNAVAILABLE;
        }

        return Response
                .status(status)
                .entity(service.getHealth())
                .build();
    }

    @GET
    @Path("/ping")
    public Response pong() {
        return Response
                .status(Response.Status.OK)
                .build();
    }

}
