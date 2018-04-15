package com.example.core.rest;

import com.example.core.validation.RequestValidation;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.math.BigInteger;

@Stateless
@Path("/job")
public class JobRest {

    private static final Logger log = Logger.getLogger(JobRest.class);

    @GET
    @Path("/{tenantId}/{accountId}")
    public Response getJobs(
            @PathParam("tenantId") @DefaultValue("0") String tenantId,
            @PathParam("accountId") @DefaultValue("0") BigInteger accountId
    ) {
        if (!RequestValidation.validateGetJobs(tenantId, accountId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.NOT_IMPLEMENTED)
                .build();
    }
}
