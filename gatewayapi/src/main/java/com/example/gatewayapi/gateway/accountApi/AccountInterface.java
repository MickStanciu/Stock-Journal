package com.example.gatewayapi.gateway.accountApi;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public interface AccountInterface {

    @GET
    @Path("/{tenantId}")
    @Produces({MediaType.APPLICATION_JSON})
    Response accountByNameAndPassword(
            @PathParam("tenantId") String tenantId,
            @QueryParam("name") String name,
            @QueryParam("password") String password
    );
}
