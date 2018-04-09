package com.example.web.gateway;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;

public interface GatewayApiInterface {

    @GET
    @Path("/auth/{tenantId}")
    @Produces(MediaType.APPLICATION_JSON)
    Response authenticate (
        @PathParam("tenantId") String tenantId,
        @QueryParam("name") String name,
        @QueryParam("password") String password
    );

    @GET
    @Path("/account/{tenantId}/{accountId}")
    @Produces({MediaType.APPLICATION_JSON})
    Response account(
            @HeaderParam("authkey") String token,
            @PathParam("tenantId") String tenantId,
            @PathParam("accountId") BigInteger accountId
    );
}
