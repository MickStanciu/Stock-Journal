package com.example.gatewayapi.controller;

import com.example.account.model.AccountModel;
import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
@Path("/api/v1/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Path("/{accountId}")
    public Response getAccountDetails(
            @HeaderParam("authkey") String token,
            @PathParam("accountId") @DefaultValue("0") BigInteger accountId
    ) {
        //todo validate input
        List<ErrorDto> errors = new ArrayList<>();
        Response.Status responseStatus = Response.Status.OK;

        AccountModel response = null;
        try {
            String tenantId = getTenantId(token);
            response = accountService.getAccount(tenantId, accountId);
        } catch (GatewayApiException e) {
            log.error(e.getMessage(), e);
            errors.add(new ErrorDto(ExceptionCode.ACCOUNT_NOT_FOUND.name(), ExceptionCode.ACCOUNT_NOT_FOUND.getMessage()));
            responseStatus = Response.Status.NOT_FOUND;
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<AccountModel>()
                .withData(response)
                .withErrors(errors)
                .build();

        return Response.status(responseStatus)
                .entity(responseEnvelope)
                .build();
    }

}
