package com.example.account.rest;

import com.example.account.exception.ExceptionCodes;
import com.example.account.model.Account;
import com.example.account.service.AccountService;
import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import com.sun.tools.internal.ws.wsdl.document.jaxws.Exception;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Stateless
@Path("/")
@Produces("application/json")
public class AccountRest {

    private static final Logger log = Logger.getLogger(AccountRest.class);

    @Inject
    private AccountService accountService;

    @GET
    public String test() {
        return "It works";
    }

    @GET
    @Path("/{tenantId}")
    public Response getAccount(
            @PathParam("tenantId") @DefaultValue("0") String tenantId,
            @QueryParam("name") @DefaultValue("") String name,
            @QueryParam("password") @DefaultValue("") String password
    ) {
        if (tenantId.length() == 0 || name.length() == 0 || password.length() == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Account account = accountService.getAccount(name, password, tenantId);
        List<ErrorDto> errors = new ArrayList<>();
        if (account == null) {
            errors.add(new ErrorDto(ExceptionCodes.ACCOUNT_NOT_FOUND.name(), ExceptionCodes.ACCOUNT_NOT_FOUND.getMessage()));
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.ResponseEnvelopeBuilder<Account>()
                .data(account)
                .errors(errors)
                .build();

        return Response.status(Response.Status.OK)
                .entity(responseEnvelope)
                .build();
    }
}
