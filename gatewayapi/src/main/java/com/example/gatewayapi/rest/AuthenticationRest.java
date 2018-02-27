package com.example.gatewayapi.rest;

import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.model.Token;
import com.example.gatewayapi.service.AuthenticationService;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Stateless
@Path("/auth")
@Produces("application/json")
public class AuthenticationRest {

    private static final Logger log = Logger.getLogger(AuthenticationRest.class);

    @Inject
    private AuthenticationService authService;

    @GET
    @Path("/{tenantId}")
    public Response authenticate(
            @PathParam("tenantId") @DefaultValue("0") String tenantId,
            @QueryParam("name") @DefaultValue("") String name,
            @QueryParam("password") @DefaultValue("") String password
    ) {
        //todo validate input
        try {
            String token = authService.authenticate(tenantId, name, password);
            return Response.status(Response.Status.OK).entity(new Token(token)).build();
        } catch (GatewayApiException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
