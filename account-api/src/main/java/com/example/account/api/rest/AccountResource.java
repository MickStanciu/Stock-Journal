package com.example.account.api.rest;

import com.example.account.api.exception.AccountException;
import com.example.account.api.facade.AccountFacade;
import com.example.account.api.spec.exception.ExceptionCode;
import com.example.account.api.spec.model.AccountModel;
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
            throw new AccountException(ExceptionCode.BAD_REQUEST);
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
    public ResponseEntity<AccountModel> accountById(
            @PathVariable("tenantId") String tenantId,
            @PathVariable("accountId") long accountId
    ) throws AccountException {
        if (!RequestValidation.validateGetAccount(tenantId, accountId)) {
            throw new AccountException(ExceptionCode.BAD_REQUEST);
        }

        Optional<AccountModel> accountOptional = accountFacade.getAccount(tenantId, accountId);

        if (!accountOptional.isPresent()) {
            throw new AccountException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountOptional.get());
    }

    @RequestMapping(value = "/relations/{tenantId}/{parentId}", method = RequestMethod.GET)
    public ResponseEntity<List<AccountModel>> getAccountByRelationship(
            @PathVariable("tenantId") String tenantId,
            @PathVariable("parentId") long parentId,
            @RequestParam("depth") Integer depth
    ) throws AccountException {
        if (!RequestValidation.validateGetAccountsByRelationship(tenantId, parentId, depth)) {
            throw new AccountException(ExceptionCode.BAD_REQUEST);
        }

        List<AccountModel> accountList = accountFacade.getAccountsByRelationship(tenantId, parentId, depth);

        if (accountList.isEmpty()) {
            throw new AccountException(ExceptionCode.ACCOUNTS_NOT_FOUND);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountList);
    }

    @RequestMapping(value = "/{tenantId}", method = RequestMethod.POST)
    public ResponseEnvelope createAccount(
            @RequestBody AccountModel account,
            @PathVariable("tenantId")  String tenantId
    ) throws AccountException {
        if (!RequestValidation.validateCreateAccount(tenantId, account)) {
            throw new AccountException(ExceptionCode.BAD_REQUEST);
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
    ) throws AccountException {
        if (!RequestValidation.validateUpdateAccount(tenantId, accountId, account)) {
            throw new AccountException(ExceptionCode.BAD_REQUEST);
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
