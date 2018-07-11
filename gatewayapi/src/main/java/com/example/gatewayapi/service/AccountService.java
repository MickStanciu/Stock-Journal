package com.example.gatewayapi.service;

import com.example.account.model.AccountModel;
import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.gateway.AccountGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigInteger;
import java.util.Optional;

@Singleton
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Inject
    private AccountGateway accountGateway;

    @PostConstruct
    public void init() {
        log.debug(AccountService.class.getName() + " was initialized");
    }

    public AccountModel getAccount(String tenantId, BigInteger accountId) throws GatewayApiException {
        Optional<AccountModel> accountOptional = accountGateway.getAccount(tenantId, accountId);
        if (!accountOptional.isPresent()) {
            throw new GatewayApiException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        return accountOptional.get();
    }
}
