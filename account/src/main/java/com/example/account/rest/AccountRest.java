package com.example.account.rest;

import com.example.account.exception.ExceptionCodes;
import com.example.account.model.response.Account;
import com.example.account.model.request.AccountDto;
import com.example.account.service.AccountService;
import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
@Path("/")
@Produces("application/json")
public class AccountRest {

    private static final Logger log = Logger.getLogger(AccountRest.class);

    @Inject
    private AccountService accountService;

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

        List<ErrorDto> errors = new ArrayList<>();

        //todo: catch all errors
        Optional<Account> accountOptional;
        try {
            accountOptional = accountService.getAccount(name, password, tenantId);
        } catch (Exception ex) {
            log.error(ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        Account account = null;
        if (!accountOptional.isPresent()) {
            errors.add(new ErrorDto(ExceptionCodes.ACCOUNT_NOT_FOUND.name(), ExceptionCodes.ACCOUNT_NOT_FOUND.getMessage()));
        } else {
            account = accountOptional.get();
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<Account>()
                .withData(account)
                .withErrors(errors)
                .build();

        return Response.status(Response.Status.OK)
                .entity(responseEnvelope)
                .build();
    }

    @POST
    @Path("/{tenantId}")
    @Consumes("application/json")
    public Response createAccount(AccountDto accountDto) {
        //sanitize the accountDto
        System.out.println(accountDto);
        log.info(accountDto);
        return Response.status(Response.Status.OK).build();
    }

}
