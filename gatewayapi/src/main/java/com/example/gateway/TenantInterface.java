package com.example.gateway;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/tenant/api")
public interface TenantInterface {

    @GET
    @Path("/{tenantId}")
    @Produces({MediaType.APPLICATION_JSON})
    ResponseEnvelope<Tenant> tenantByUUID(@PathParam("tenantId") String tenantId);

}
