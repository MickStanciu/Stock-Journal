package com.example.accountapi.rest;

import com.example.accountapi.exception.AccountException;
import com.example.accountapi.exception.ExceptionCode;
import com.example.accountapi.exception.ResourceErrorException;
import com.example.accountapi.facade.AccountFacade;
import com.example.accountapi.model.AccountModel;
import com.example.common.rest.envelope.ErrorModel;
import com.example.common.rest.envelope.ResponseEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class AccountResource {

    private static final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private AccountFacade accountFacade;

    @Autowired
    public AccountResource(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @RequestMapping(value = "/{tenantId}", method = RequestMethod.GET)
    public ResponseEntity<AccountModel> accountByEmailAndPassword(
            @PathVariable(name = "tenantId") String tenantId,
            @RequestParam(name = "email", defaultValue = "")  String email,
            @RequestParam(name = "password", defaultValue = "") String password
    ) throws AccountException {
        if (!RequestValidation.validateGetAccount(tenantId, email, password)) {
            throw new ResourceErrorException(ExceptionCode.UNKNOWN, "Invalid Request");
        }

        Optional<AccountModel> accountOptional = accountFacade.getAccount(tenantId, email, password);

        if (!accountOptional.isPresent()) {
            throw new AccountException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountOptional.get());
    }

    @RequestMapping(value = "/{tenantId}/{accountId}", method = RequestMethod.GET)
    public ResponseEnvelope accountById(
            @PathVariable("tenantId") String tenantId,
            @PathVariable("accountId") long accountId
    ) {
        if (!RequestValidation.validateGetAccount(tenantId, accountId)) {
            return null;
        }

        List<ErrorModel> errors = new ArrayList<>();

        Optional<AccountModel> accountOptional;
        try {
            accountOptional = accountFacade.getAccount(tenantId, accountId);
        } catch (Exception ex) {
            log.error("", ex);
            return null;
        }

        if (!accountOptional.isPresent()) {
            errors.add(new ErrorModel(ExceptionCode.ACCOUNT_NOT_FOUND.name(), ExceptionCode.ACCOUNT_NOT_FOUND.getMessage()));
        }

        return new ResponseEnvelope.Builder<AccountModel>()
                .withData(accountOptional.orElse(null))
                .withErrors(errors)
                .build();
    }

    @RequestMapping(value = "/relations/{tenantId}/{parentId}", method = RequestMethod.GET)
    public ResponseEnvelope getAccountByRelationship(
            @PathVariable("tenantId") String tenantId,
            @PathVariable("parentId") long parentId,
            @RequestParam("depth") Integer depth
    ) {
        if (!RequestValidation.validateGetAccountsByRelationship(tenantId, parentId, depth)) {
            return null;
        }

        List<AccountModel> accountList;
        try {
            accountList = accountFacade.getAccountsByRelationship(tenantId, parentId, depth);
        } catch (Exception ex) {
            log.error("", ex);
            return null;
        }

        List<ErrorModel> errors = new ArrayList<>();
        if (accountList.isEmpty()) {
            errors.add(new ErrorModel(ExceptionCode.ACCOUNTS_NOT_FOUND.name(), ExceptionCode.ACCOUNTS_NOT_FOUND.getMessage()));
        }

        return new ResponseEnvelope.Builder<List<AccountModel>>()
                .withData(accountList)
                .withErrors(errors)
                .build();
    }

    @RequestMapping(value = "/{tenantId}", method = RequestMethod.POST)
    public ResponseEnvelope createAccount(
            @RequestBody AccountModel account,
            @PathVariable("tenantId")  String tenantId
    ) {
        if (!RequestValidation.validateCreateAccount(tenantId, account)) {
            return null;
        }

        Optional<AccountModel> accountOptional;
        List<ErrorModel> errors = new ArrayList<>();

        try {
            accountOptional = accountFacade.createAccount(tenantId, account.getName(), account.getEmail(), account.getPassword() );
        } catch (AccountException aex) {
            log.error("", aex);
            errors.add(new ErrorModel(aex.getCode().name(), aex.getMessage()));
            accountOptional = Optional.empty();
        } catch (Exception ex) {
            //todo: move out all the Exceptions
            log.error("", ex);
            return null;
        }

        if (!accountOptional.isPresent() && errors.isEmpty()) {
            errors.add(new ErrorModel(ExceptionCode.UNKNOWN.name(), ExceptionCode.UNKNOWN.getMessage()));
        }

        return new ResponseEnvelope.Builder<AccountModel>()
                .withData(accountOptional.orElse(null))
                .withErrors(errors)
                .build();
    }

    @RequestMapping(value = "/{tenantId}/{accountId}", method = RequestMethod.POST)
    public ResponseEnvelope updateAccount(
            AccountModel account,
            @PathVariable("tenantId") String tenantId,
            @PathVariable("accountId") long accountId
    ) {
        if (!RequestValidation.validateUpdateAccount(tenantId, accountId, account)) {
            return null;
        }

        Optional<AccountModel> accountOptional;
        List<ErrorModel> errors = new ArrayList<>();

        try {
            accountOptional = accountFacade.updateAccount(tenantId, accountId, account);
        } catch (AccountException aex) {
            log.error("", aex);
            errors.add(new ErrorModel(aex.getCode().name(), aex.getMessage()));
            accountOptional = Optional.empty();
        } catch (Exception ex) {
            //todo: move out all the Exceptions
            log.error("", ex);
            return null;
        }

        if (!accountOptional.isPresent() && errors.isEmpty()) {
            errors.add(new ErrorModel(ExceptionCode.UNKNOWN.name(), ExceptionCode.UNKNOWN.getMessage()));
        }

        return new ResponseEnvelope.Builder<AccountModel>()
                .withData(accountOptional.orElse(null))
                .withErrors(errors)
                .build();
    }
}
