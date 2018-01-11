package com.example.tenant.rest;

import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Stateless
@Path("/")
@Produces("application/json")
public class TenantRest {

    private static final Logger log = Logger.getLogger(TenantRest.class);

    @GET
    @Path("/{tenantId}")
    public Response getTenant(@PathParam("tenantId") @DefaultValue("0") String tenantId) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
