package com.example.tenant.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/health")
public class HealthCheck {

    @GET
    @Path("/check")
    @Produces("text/plain")
    public Response check() {
        return Response.ok("Hello from WildFly Swarm!").build();
    }
}
