package com.example.gateway;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/tenant/api")
public interface TenantInterface {

    @GET
    @Path("/{tenantId}")
    @Produces({MediaType.APPLICATION_JSON})
    ResponseEnvelope<Tenant> tenantByUUID(@PathParam("tenantId") String tenantId);

}
