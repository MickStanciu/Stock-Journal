package com.example.gatewayapi.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public interface AuthenticationRestInterface {

    @GET
    @Path("/{tenantId}")
    Response authenticate (
        @PathParam("tenantId") String tenantId,
        @QueryParam("email") String email,
        @QueryParam("password") String password
    );
}
