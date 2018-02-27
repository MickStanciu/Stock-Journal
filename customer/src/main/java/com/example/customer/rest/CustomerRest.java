package com.example.customer.rest;

import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.customer.model.Customer;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        List<Customer> customers;
        if (active) {
            customers = customerService.getActiveCustomers(tenantId);
        } else {
            customers = customerService.getInactiveCustomers(tenantId);
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<List<Customer>>()
                .withData(customers)
                .withErrors(Collections.emptyList())
                .build();

        return Response.status(Response.Status.OK)
                .entity(responseEnvelope)
                .build();
    }

}
