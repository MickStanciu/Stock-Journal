package com.example.tenant.rest;

import com.example.tenant.service.HealthService;

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
    public Response check() {
        Response.Status status = Response.Status.OK;
        if (!service.isOk()) {
            status = Response.Status.SERVICE_UNAVAILABLE;
        }

        return Response.status(status).entity(service.getHealth()).build();
    }
}
