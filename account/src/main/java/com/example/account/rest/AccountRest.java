package com.example.account.rest;

import com.example.account.exception.AccountException;
import com.example.account.exception.ExceptionCode;
import com.example.account.facade.AccountFacade;
import com.example.account.model.Account;
import com.example.account.validation.RequestValidation;
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
    private AccountFacade accountFacade;

    @GET
    @Path("/{tenantId}")
    public Response getAccount(
            @PathParam("tenantId") @DefaultValue("0") String tenantId,
            @QueryParam("name") @DefaultValue("") String name,
            @QueryParam("password") @DefaultValue("") String password
    ) {
        if (!RequestValidation.validateGetAccount(tenantId, name, password)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<ErrorDto> errors = new ArrayList<>();

        //todo: catch all errors
        Optional<Account> accountOptional;
        try {
            accountOptional = accountFacade.getAccount(tenantId, name, password);
        } catch (Exception ex) {
            log.error(ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        Account account = null;
        if (!accountOptional.isPresent()) {
            errors.add(new ErrorDto(ExceptionCode.ACCOUNT_NOT_FOUND.name(), ExceptionCode.ACCOUNT_NOT_FOUND.getMessage()));
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
    public Response createAccount(Account account, @PathParam("tenantId") @DefaultValue("0") String tenantId) {
        //todo: sanitize the accountDto
        //todo: catch bad params: com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException

        if (!RequestValidation.validateCreateAccount(tenantId, account)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Optional<Account> accountOptional;
        List<ErrorDto> errors = new ArrayList<>();

        try {
            accountOptional = accountFacade.createAccount(tenantId, account.getName(), account.getPassword() );
        } catch (AccountException aex) {
            log.error(aex);
            errors.add(new ErrorDto(aex.getCode().name(), aex.getMessage()));
            accountOptional = Optional.empty();
        } catch (Exception ex) {
            //todo: move out all the Exceptions
            log.error(ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        account = null;
        if (!accountOptional.isPresent()) {
            if (errors.isEmpty()) {
                errors.add(new ErrorDto(ExceptionCode.UNKNOWN.name(), ExceptionCode.UNKNOWN.getMessage()));
            }
        } else {
            account = accountOptional.get();
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<Account>()
                .withData(account)
                .withErrors(errors)
                .build();

        //todo: move this out
        Response.Status status = Response.Status.CREATED;
        if (errors.size() != 0) {
            if (errors.get(0).getCode().equals("ACCOUNT_EXISTS")) {
                status = Response.Status.CONFLICT;
            } else {
                status = Response.Status.BAD_REQUEST;
            }
        }

        return Response.status(status)
                .entity(responseEnvelope)
                .build();
    }


    @PUT
    @Path("/{tenantId}/{accountId}")
    @Consumes("application/json")
    public Response updateAccount(
            Account account,
            @PathParam("tenantId") @DefaultValue("0") String tenantId,
            @PathParam("accountId") @DefaultValue("0") Integer accountId
           ) {

        if (!RequestValidation.validateUpdateAccount(tenantId, accountId, account)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).build();
    }


}
