package com.example.account.controller;

import com.example.account.exception.AccountException;
import com.example.account.exception.ExceptionCode;
import com.example.account.facade.AccountFacade;
import com.example.account.model.AccountModel;
import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Path("/api/v1")
@Produces("application/json")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private AccountFacade accountFacade;

    @Autowired
    public AccountController(AccountFacade accountFacade) {
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

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
