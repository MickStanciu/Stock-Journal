package com.example.gatewayapi.rest;

import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.model.AuthToken;
import com.example.gatewayapi.service.AuthenticationService;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationRest implements AuthenticationRestInterface {

    private static final Logger log = Logger.getLogger(AuthenticationRest.class);

    @Inject
    private AuthenticationService authService;

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

        AuthToken response = null;
        try {
            response = authService.getAuthResponse(tenantId, email, password);
        } catch (GatewayApiException e) {
            errors.add(new ErrorDto(ExceptionCode.REQUEST_NOT_AUTHORIZED.name(), ExceptionCode.REQUEST_NOT_AUTHORIZED.getMessage()));
            responseStatus = Response.Status.UNAUTHORIZED;
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<AuthToken>()
                .withData(response)
                .withErrors(errors)
                .build();

        return Response.status(responseStatus)
                .entity(responseEnvelope)
                .build();
    }
}
