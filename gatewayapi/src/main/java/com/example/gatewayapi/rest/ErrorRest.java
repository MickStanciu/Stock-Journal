package com.example.gatewayapi.rest;

import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Stateless
@Path("/error")
public class ErrorRest {

    private static final Logger log = Logger.getLogger(ErrorRest.class);

    @GET
    @Path("/401")
    public Response get401() {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
