package com.example.gatewayapi.service;

import com.example.account.model.AccountModel;
import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.gateway.AccountGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private AccountGateway accountGateway;

    @Autowired
    public AccountService(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public AccountModel getAccount(String tenantId, BigInteger accountId) throws GatewayApiException {
        Optional<AccountModel> accountOptional = accountGateway.getAccount(tenantId, accountId);
        if (!accountOptional.isPresent()) {
            throw new GatewayApiException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        return accountOptional.get();
    }
}
