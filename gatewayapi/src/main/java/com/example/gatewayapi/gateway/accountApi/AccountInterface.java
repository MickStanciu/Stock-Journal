package com.example.gatewayapi.gateway.accountApi;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;

@Path("/")
public interface AccountInterface {

    @GET
    @Path("/{tenantId}")
    @Produces({MediaType.APPLICATION_JSON})
    Response accountByEmailAndPassword(
            @PathParam("tenantId") String tenantId,
            @QueryParam("email") String email,
            @QueryParam("password") String password
    );

    @GET
    @Path("/{tenantId}/{accountId}")
    @Produces({MediaType.APPLICATION_JSON})
    Response accountById(
            @PathParam("tenantId") String tenantId,
            @PathParam("accountId") BigInteger accountId
    );
}
