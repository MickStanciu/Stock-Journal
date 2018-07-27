package com.example.gatewayapi.rest;

import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.model.AuthTokenModel;
import com.example.gatewayapi.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/rest/v1/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationResource.class);

    @Inject
    private AuthenticationService authService;

    @GET
    @Path("/{tenantId}")
    public Response authenticate(
            @PathParam("tenantId") String tenantId,
            @QueryParam("email") String email,
            @QueryParam("password") String password
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

        return Response
                .status(responseStatus)
                .entity(responseEnvelope)
                .build();
    }
}
