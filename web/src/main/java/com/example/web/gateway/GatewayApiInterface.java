package com.example.web.gateway;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/api")
public interface GatewayApiInterface {

    @GET
    @Path("/auth/{tenantId}")
    @Produces(MediaType.APPLICATION_JSON)
    AuthToken authenticate (
        @PathParam("tenantId") String tenantId,
        @QueryParam("name") String name,
        @QueryParam("password") String password
    );
}
