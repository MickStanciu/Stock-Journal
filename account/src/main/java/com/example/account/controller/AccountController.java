package com.example.account.controller;

import com.example.account.facade.AccountFacade;
import com.example.account.model.AccountModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

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

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
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
