package com.example.account.controller;

import com.example.account.exception.ExceptionCode;
import com.example.account.facade.AccountFacade;
import com.example.account.model.AccountModel;
import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private AccountFacade accountFacade;

    @Autowired
    public AccountController(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @RequestMapping(value = "/{tenantId}", method = RequestMethod.GET)
    public ResponseEntity<?> accountByEmailAndPassword(
            @PathVariable(name = "tenantId") String tenantId,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        if (!RequestValidation.validateGetAccount(tenantId, email, password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        //todo: catch all errors
        Optional<AccountModel> accountOptional;
        try {
            accountOptional = accountFacade.getAccount(tenantId, email, password);
        } catch (Exception ex) {
            log.error("", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

        return ResponseEntity.status(HttpStatus.OK)
                .body(responseEnvelope);
    }

    @RequestMapping(value = "/{tenantId}/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<?> accountById(
            @PathVariable(name = "tenantId") String tenantId,
            @PathVariable(name = "accountId") BigInteger accountId
    ) {
        if (!RequestValidation.validateGetAccount(tenantId, accountId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @RequestMapping(value = "/relations/{tenantId}/{parentId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAccountByRelationship(
            @PathVariable(name = "tenantId") String tenantId,
            @PathVariable(name = "parentId") BigInteger parentId,
            @RequestParam("depth") Integer depth
    ) {
        if (!RequestValidation.validateGetAccountsByRelationship(tenantId, parentId, depth)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @RequestMapping(value = "/{tenantId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAccount(
            AccountModel account,
            @PathVariable(name = "tenantId") String tenantId
    ) {
        if (!RequestValidation.validateCreateAccount(tenantId, account)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @RequestMapping(value = "/{tenantId}/{accountId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAccount(
            AccountModel account,
            @PathVariable(name = "tenantId") String tenantId,
            @PathVariable(name = "accountId") BigInteger accountId
    ) {
        if (!RequestValidation.validateUpdateAccount(tenantId, accountId, account)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
