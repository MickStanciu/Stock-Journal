package com.example.core.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface TimesheetRestInterface {

    @GET
    @Path("/{tenantId}/{accountId}")
    Response getTimesheetEntries(
            @PathParam("tenantId") @DefaultValue("0") String tenantId,
            @PathParam("accountId") @DefaultValue("0") BigInteger accountId,
            @QueryParam("from") String from,
            @QueryParam("to") String to
    );
}
