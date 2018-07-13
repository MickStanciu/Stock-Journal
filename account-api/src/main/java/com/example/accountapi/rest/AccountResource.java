package com.example.accountapi.rest;

import com.example.account.model.AccountModel;
import com.example.accountapi.exception.AccountException;
import com.example.accountapi.exception.ExceptionCode;
import com.example.accountapi.facade.AccountFacade;
import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    private static final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private AccountFacade accountFacade;

    @Inject
    public AccountResource(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

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
            log.error("", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        List<ErrorDto> errors = new ArrayList<>();
        if (!accountOptional.isPresent()) {
            errors.add(new ErrorDto(ExceptionCode.ACCOUNT_NOT_FOUND.name(), ExceptionCode.ACCOUNT_NOT_FOUND.getMessage()));
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<AccountModel>()
                .withData(accountOptional.orElse(null))
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
            log.error("", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        if (!accountOptional.isPresent()) {
            errors.add(new ErrorDto(ExceptionCode.ACCOUNT_NOT_FOUND.name(), ExceptionCode.ACCOUNT_NOT_FOUND.getMessage()));
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<AccountModel>()
                .withData(accountOptional.orElse(null))
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
            log.error("", ex);
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
    @Consumes("application/json")
    public Response createAccount(
            AccountModel account,
            @PathParam("tenantId") @DefaultValue("0") String tenantId
    ) {
        if (!RequestValidation.validateCreateAccount(tenantId, account)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Optional<AccountModel> accountOptional;
        List<ErrorDto> errors = new ArrayList<>();

        try {
            accountOptional = accountFacade.createAccount(tenantId, account.getName(), account.getEmail(), account.getPassword() );
        } catch (AccountException aex) {
            log.error("", aex);
            errors.add(new ErrorDto(aex.getCode().name(), aex.getMessage()));
            accountOptional = Optional.empty();
        } catch (Exception ex) {
            //todo: move out all the Exceptions
            log.error("", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        if (!accountOptional.isPresent() && errors.isEmpty()) {
            errors.add(new ErrorDto(ExceptionCode.UNKNOWN.name(), ExceptionCode.UNKNOWN.getMessage()));
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
                .withData(accountOptional.orElse(null))
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
            log.error("", aex);
            errors.add(new ErrorDto(aex.getCode().name(), aex.getMessage()));
            accountOptional = Optional.empty();
        } catch (Exception ex) {
            //todo: move out all the Exceptions
            log.error("", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        if (!accountOptional.isPresent() && errors.isEmpty()) {
            errors.add(new ErrorDto(ExceptionCode.UNKNOWN.name(), ExceptionCode.UNKNOWN.getMessage()));
        }

        Response.Status status = Response.Status.ACCEPTED;
        if (errors.size() != 0) {
            status = Response.Status.BAD_REQUEST;
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<AccountModel>()
                .withData(accountOptional.orElse(null))
                .withErrors(errors)
                .build();

        return Response.status(status)
                .entity(responseEnvelope)
                .build();
    }
}
