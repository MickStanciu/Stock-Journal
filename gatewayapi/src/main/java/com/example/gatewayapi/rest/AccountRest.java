package com.example.gatewayapi.rest;

import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.gateway.accountApi.Account;
import com.example.gatewayapi.model.AccountDto;
import com.example.gatewayapi.model.AuthToken;
import com.example.gatewayapi.service.AccountService;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/account")
@Produces("application/json")
public class AccountRest {

    private static final Logger log = Logger.getLogger(AccountRest.class);

    @Inject
    private AccountService accountService;

    @GET
    @Path("/{tenantId}/{accountId}")
    public Response getAccountDetails(
            @HeaderParam("authkey") String token,
            @PathParam("tenantId") @DefaultValue("0") String tenantId,
            @PathParam("accountId") @DefaultValue("0") BigInteger accountId
    ) {
        //todo validate input
        List<ErrorDto> errors = new ArrayList<>();
        Response.Status responseStatus = Response.Status.OK;

        Account response = null;
        try {
            response = accountService.getAccount(tenantId, accountId);
        } catch (GatewayApiException e) {
            errors.add(new ErrorDto(ExceptionCode.ACCOUNT_NOT_FOUND.name(), ExceptionCode.ACCOUNT_NOT_FOUND.getMessage()));
            responseStatus = Response.Status.NOT_FOUND;
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<Account>()
                .withData(response)
                .withErrors(errors)
                .build();

        return Response.status(responseStatus)
                .entity(responseEnvelope)
                .build();
    }
}
