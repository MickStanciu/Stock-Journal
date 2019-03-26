package com.example.gateway.api.resource;

import com.example.account.api.spec.model.AccountModel;
import com.example.gateway.api.exception.ExceptionCode;
import com.example.gateway.api.exception.GatewayApiException;
import com.example.gateway.api.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/account", produces = "application/json")
public class AccountResource extends AbstractResource {

    private static final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private AccountService accountService;

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<AccountModel> getAccountDetails(
            @RequestHeader(name = "authkey") String token,
            @PathVariable(name = "accountId") String accountId) throws GatewayApiException {
        //todo validate input

        Optional<AccountModel> accountOptional = accountService.getAccount(accountId);

        if (accountOptional.isEmpty()) {
            throw new GatewayApiException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountOptional.get());
    }
}
