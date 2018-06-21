package com.example.gatewayapi.controller;

import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.model.AuthTokenModel;
import com.example.gatewayapi.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@Component
@Path("/api/v1/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationRest {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationRest.class);

    private AuthenticationService authService;

    @Autowired
    public AuthenticationRest(AuthenticationService authService) {
        this.authService = authService;
    }

    @GET
    @Path("/{tenantId}")
    public Response authenticate(
            @PathParam("tenantId") @DefaultValue("0") String tenantId,
            @QueryParam("email") @DefaultValue("") String email,
            @QueryParam("password") @DefaultValue("") String password
    ) {
        //todo validate input

        List<ErrorDto> errors = new ArrayList<>();
        Response.Status responseStatus = Response.Status.OK;

        AuthTokenModel response = null;
        try {
            response = authService.getAuthResponse(tenantId, email, password);
        } catch (GatewayApiException e) {
            errors.add(new ErrorDto(ExceptionCode.REQUEST_NOT_AUTHORIZED.name(), ExceptionCode.REQUEST_NOT_AUTHORIZED.getMessage()));
            responseStatus = Response.Status.UNAUTHORIZED;
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<AuthTokenModel>()
                .withData(response)
                .withErrors(errors)
                .build();

        return Response.status(responseStatus)
                .entity(responseEnvelope)
                .build();
    }
}
