package com.example.gatewayapi.rest;

import com.example.accountapi.model.AccountModel;
import com.example.gatewayapi.exception.GatewayApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/account", produces = "application/json")
public class AccountResource extends AbstractResource {

    private static final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private AccountService accountService;

    @Autowired
    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostConstruct
    public void init() {
        log.debug(AccountResource.class.getName() + " was initialized");
    }


    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<AccountModel> getAccountDetails(
            @RequestHeader(name = "authkey") String token,
            @PathVariable(name = "accountId") BigInteger accountId) throws GatewayApiException {
        //todo validate input

        String tenantId = getTenantId(token);
        Optional<AccountModel> accountOptional = accountService.getAccount(tenantId, accountId);

        if (!accountOptional.isPresent()) {

        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountOptional.get());

    }
}
