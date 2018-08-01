package com.example.gatewayapi.rest;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/burger")
@Produces(MediaType.APPLICATION_JSON)
public class BurgerController {

    @POST
    @Path("/orders.json")
    public Response getAccountDetails(@Context HttpServletResponse servletResponse, String order) {
        System.out.println("/orders.json " + order);

        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
        return Response
                .status(Response.Status.OK).build();
    }
}
