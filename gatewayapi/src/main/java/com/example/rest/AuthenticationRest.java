package com.example.rest;

import com.example.exception.GatewayApiException;
import com.example.service.AuthenticationService;
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
    public Response getAccountDetails(
            @PathParam("tenantId") @DefaultValue("0") String tenantId,
            @QueryParam("name") @DefaultValue("") String name,
            @QueryParam("password") @DefaultValue("") String password
    ) {
        //todo validate input
        try {
            authService.getAccountDetails(tenantId, name, password);
        } catch (GatewayApiException e) {
            //todo add error list to response envelope
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).build();
    }
}
