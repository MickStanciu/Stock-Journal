package com.example.gatewayapi.gateway.tenantApi;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public interface TenantInterface {

    @GET
    @Path("/{tenantId}")
    @Produces({MediaType.APPLICATION_JSON})
    Response tenantByUUID(@PathParam("tenantId") String tenantId);

}
