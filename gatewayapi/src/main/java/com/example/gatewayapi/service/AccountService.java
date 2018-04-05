package com.example.gatewayapi.service;

import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.gateway.accountApi.Account;
import com.example.gatewayapi.gateway.accountApi.AccountGateway;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigInteger;
import java.util.Optional;

@Stateless
public class AccountService {

    private static final Logger log = Logger.getLogger(AccountService.class);

    @Inject
    private AccountGateway accountGateway;

    public Account getAccount(String tenantId, BigInteger accountId) throws GatewayApiException {
        Optional<Account> accountOptional = accountGateway.getAccount(tenantId, accountId);
        if (!accountOptional.isPresent()) {
            throw new GatewayApiException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        return accountOptional.get();
    }
}
