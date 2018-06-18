//package com.example.account.controller;
//
//
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.math.BigInteger;
//
//@Path("/")
//@Produces(MediaType.APPLICATION_JSON)
//public interface AccountRestInterface {
//
//    @GET
//    @Path("/{tenantId}")
//    Response accountByEmailAndPassword(
//            @PathParam("tenantId") @DefaultValue("0") String tenantId,
//            @QueryParam("email") @DefaultValue("") String email,
//            @QueryParam("password") @DefaultValue("") String password
//    );
//
//    @GET
//    @Path("/{tenantId}/{accountId}")
//    Response accountById(
//            @PathParam("tenantId") @DefaultValue("0") String tenantId,
//            @PathParam("accountId") @DefaultValue("0") BigInteger accountId
//    );
//}
