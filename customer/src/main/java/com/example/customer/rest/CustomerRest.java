package com.example.customer.rest;

import com.example.customer.service.CustomerService;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Stateless
@Path("/")
@Produces("application/json")
public class CustomerRest {

    private static final Logger log = Logger.getLogger(CustomerRest.class);

    @Inject
    private CustomerService customerService;

    @GET
    @Path("/{tenantId}")
    public Response getAllCustomers(
            @PathParam("tenantId") @DefaultValue("0") String tenantId,
            @QueryParam("active") @DefaultValue("true") Boolean active) {

        //todo: RequestValidation
        //todo: service class
        return Response.status(Response.Status.OK).build();
    }

}
