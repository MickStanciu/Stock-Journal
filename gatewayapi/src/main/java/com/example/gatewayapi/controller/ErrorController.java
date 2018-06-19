package com.example.gatewayapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/api/v1/error")
@Produces(MediaType.APPLICATION_JSON)
public class ErrorController {

    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    @GET
    @Path("/401")
    public Response e401() {
        log.error("Not authorized");
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
