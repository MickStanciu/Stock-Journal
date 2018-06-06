package com.example.account.rest;

import com.example.account.service.HealthService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Stateless
@Path("/health")
@Produces("application/json")
public class HealthRest {

    @Inject
    private HealthService service;

    @GET
    @Path("/check")
    public Response checkFirstRecord() {
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
