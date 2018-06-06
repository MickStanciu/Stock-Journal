package com.example.account.rest;

import com.example.account.exception.AccountException;
import com.example.account.exception.ExceptionCode;
import com.example.account.facade.AccountFacade;
import com.example.account.model.AccountModel;
import com.example.account.validation.RequestValidation;
import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
@Path("/")
@Produces("application/json")
public class AccountRest implements AccountRestInterface {

    private static final Logger log = Logger.getLogger(AccountRest.class);

    @Inject
    private AccountFacade accountFacade;

    @GET
    @Path("/{tenantId}")
    public Response accountByEmailAndPassword(
            @PathParam("tenantId") @DefaultValue("0") String tenantId,
            @QueryParam("email") @DefaultValue("") String email,
            @QueryParam("password") @DefaultValue("") String password
    ) {
        if (!RequestValidation.validateGetAccount(tenantId, email, password)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


        //todo: catch all errors
        Optional<AccountModel> accountOptional;
        try {
            accountOptional = accountFacade.getAccount(tenantId, email, password);
        } catch (Exception ex) {
            log.error(ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        List<ErrorDto> errors = new ArrayList<>();
        AccountModel account = null;
        if (!accountOptional.isPresent()) {
            errors.add(new ErrorDto(ExceptionCode.ACCOUNT_NOT_FOUND.name(), ExceptionCode.ACCOUNT_NOT_FOUND.getMessage()));
        } else {
            account = accountOptional.get();
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<AccountModel>()
                .withData(account)
                .withErrors(errors)
                .build();

        return Response.status(Response.Status.OK)
                .entity(responseEnvelope)
                .build();
    }

    @GET
    @Path("/{tenantId}/{accountId}")
    public Response accountById(
            @PathParam("tenantId") @DefaultValue("0") String tenantId,
            @PathParam("accountId") @DefaultValue("0") BigInteger accountId
    ) {
        if (!RequestValidation.validateGetAccount(tenantId, accountId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<ErrorDto> errors = new ArrayList<>();

        Optional<AccountModel> accountOptional;
        try {
            accountOptional = accountFacade.getAccount(tenantId, accountId);
        } catch (Exception ex) {
            log.error(ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        AccountModel account = null;
        if (!accountOptional.isPresent()) {
            errors.add(new ErrorDto(ExceptionCode.ACCOUNT_NOT_FOUND.name(), ExceptionCode.ACCOUNT_NOT_FOUND.getMessage()));
        } else {
            account = accountOptional.get();
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<AccountModel>()
                .withData(account)
                .withErrors(errors)
                .build();

        return Response.status(Response.Status.OK)
                .entity(responseEnvelope)
                .build();
    }

    @GET
    @Path("/relations/{tenantId}/{parentId}")
    public Response getAccountByRelationship(
            @PathParam("tenantId") @DefaultValue("0") String tenantId,
            @PathParam("parentId") @DefaultValue("0") BigInteger parentId,
            @QueryParam("depth") Integer depth
    ) {
        if (!RequestValidation.validateGetAccountsByRelationship(tenantId, parentId, depth)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<AccountModel> accountList;
        try {
            accountList = accountFacade.getAccountsByRelationship(tenantId, parentId, depth);
        } catch (Exception ex) {
            log.error(ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        List<ErrorDto> errors = new ArrayList<>();
        if (accountList.isEmpty()) {
            errors.add(new ErrorDto(ExceptionCode.ACCOUNTS_NOT_FOUND.name(), ExceptionCode.ACCOUNTS_NOT_FOUND.getMessage()));
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<List<AccountModel>>()
                .withData(accountList)
                .withErrors(errors)
                .build();

        return Response.status(Response.Status.OK)
                .entity(responseEnvelope)
                .build();
    }


    @POST
    @Path("/{tenantId}")
    @Consumes("appliaccount_role_infocation/json")
    public Response createAccount(AccountModel account, @PathParam("tenantId") @DefaultValue("0") String tenantId) {
        //todo: sanitize the accountDto
        //todo: catch bad params: com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException

        if (!RequestValidation.validateCreateAccount(tenantId, account)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Optional<AccountModel> accountOptional;
        List<ErrorDto> errors = new ArrayList<>();

        try {
            accountOptional = accountFacade.createAccount(tenantId, account.getEmail(), account.getPassword() );
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


        Response.Status status = Response.Status.CREATED;
        if (errors.size() != 0) {
            if (errors.get(0).getCode().equals("ACCOUNT_EXISTS")) {
                status = Response.Status.CONFLICT;
            } else {
                status = Response.Status.BAD_REQUEST;
            }
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<AccountModel>()
                .withData(account)
                .withErrors(errors)
                .build();

        return Response.status(status)
                .entity(responseEnvelope)
                .build();
    }


    @PUT
    @Path("/{tenantId}/{accountId}")
    @Consumes("application/json")
    public Response updateAccount(
            AccountModel account,
            @PathParam("tenantId") @DefaultValue("0") String tenantId,
            @PathParam("accountId") @DefaultValue("0") BigInteger accountId
           ) {

        if (!RequestValidation.validateUpdateAccount(tenantId, accountId, account)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Optional<AccountModel> accountOptional;
        List<ErrorDto> errors = new ArrayList<>();

        try {
            accountOptional = accountFacade.updateAccount(tenantId, accountId, account);
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

        Response.Status status = Response.Status.ACCEPTED;
        if (errors.size() != 0) {
            status = Response.Status.BAD_REQUEST;
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<AccountModel>()
                .withData(account)
                .withErrors(errors)
                .build();

        return Response.status(status)
                .entity(responseEnvelope)
                .build();
    }


}