package com.example.gatewayapi.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/rest/v1/error")
@Produces(MediaType.APPLICATION_JSON)
public class ErrorResource {

    private static final Logger log = LoggerFactory.getLogger(ErrorResource.class);

    @GET
    @Path("/401")
    public Response e401() {
        log.error("Not authorized");
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
