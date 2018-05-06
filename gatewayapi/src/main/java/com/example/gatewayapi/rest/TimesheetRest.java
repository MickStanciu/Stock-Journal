package com.example.gatewayapi.rest;

import com.example.gatewayapi.service.AccountService;
import com.example.gatewayapi.service.TimesheetService;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigInteger;

@Stateless
@Path("/timesheet")
@Produces("application/json")
public class TimesheetRest {

    private static final Logger log = Logger.getLogger(TimesheetRest.class);

    private AccountService accountService;

    @Inject
    private TimesheetService timesheetService;


    @GET
    @Path("/{accountId}")
    public Response getAccountDetails(
            @HeaderParam("authkey") String token,
            @PathParam("accountId") @DefaultValue("0") BigInteger accountId,
            @QueryParam("from") String from,
            @QueryParam("to") String to
    ) {
        return Response.noContent().build();
    }

}
