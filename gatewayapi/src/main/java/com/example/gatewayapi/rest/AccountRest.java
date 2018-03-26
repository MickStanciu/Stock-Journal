package com.example.gatewayapi.rest;

import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Stateless
@Path("/account")
@Produces("application/json")
public class AccountRest {

    private static final Logger log = Logger.getLogger(AccountRest.class);

    @GET
    @Path("/{tenantId")
    public Response getAccountDetails() {
        log.error("NOT IMPLEMENTED");
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
}
