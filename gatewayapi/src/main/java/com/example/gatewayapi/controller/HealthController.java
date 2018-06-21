package com.example.gatewayapi.controller;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Component
@Produces("application/json")
@Path("/api/v1/health")
public class HealthController {

    @GET
    @Path("/check")
    public Response pong() {
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/check2")
    public Response pong2() {
        return Response.status(Response.Status.OK).entity("Hey").build();
    }
}
