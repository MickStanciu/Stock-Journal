package com.example.gatewayapi.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/burger")
@Produces(MediaType.APPLICATION_JSON)
public class BurgerController {

    @POST
    @Path("/orders.json")
    public Response getAccountDetails(String order) {
        System.out.println("/orders.json " + order);

        return Response.status(Response.Status.OK).build();
    }
}


/*

{"ingredients":{"salad":0,"bacon":1,"cheese":0,"meat":0},"price":4.7,"customer":{"name":"Max S","address":{"street":"Test","zipCode":"1234","country":"Australia"},"email":"test@test.com","deliveryMethod":"fastest"}}

 */