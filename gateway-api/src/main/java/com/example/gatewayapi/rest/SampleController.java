package com.example.gatewayapi.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/sample")
@Produces(MediaType.APPLICATION_JSON)
public class SampleController {

    @POST
    @Path("/orders.json")
    public Response processOrders(String order) throws InterruptedException {
        System.out.println("/orders.json " + order);
        Thread.sleep(1000);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/ingredients.json")
    public Response getIngredients() throws InterruptedException {
        System.out.println("/ingredients.json");
        Thread.sleep(1000);
        String bla = "{\"salad\":1,\"bacon\":1,\"cheese\":1,\"meat\":1}";
        return Response.status(Response.Status.OK).entity(bla).build();
    }

    @GET
    @Path("/users")
    public Response getUsers() {
        System.out.println("/users");
        String bla = "{\"username\":\"Mick\"}";
        return Response.status(Response.Status.OK).entity(bla).build();
    }
}
