package com.example.rest;

import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Stateless
@Path("/")
@Produces("application/json")
public class AuthenticationRest {

    private static final Logger log = Logger.getLogger(AuthenticationRest.class);

    @GET
    @Path("/ping")
    public Response pong() {
        log.info("INFO PONG");
        return Response.status(Response.Status.OK).entity("Pong").build();
    }
}
