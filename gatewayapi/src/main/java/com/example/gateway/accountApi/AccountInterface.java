package com.example.gateway.accountApi;

import com.example.gateway.ResponseEnvelope;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/account/api")
public interface AccountInterface {

    @GET
    @Path("/{tenantId}")
    @Produces({MediaType.APPLICATION_JSON})
    ResponseEnvelope<Account> accountByNameAndPassword(
            @PathParam("tenantId") String tenantId,
            @QueryParam("name") String name,
            @QueryParam("password") String password
    );
}
