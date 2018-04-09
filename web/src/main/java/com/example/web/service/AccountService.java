package com.example.web.service;

import com.example.web.exception.ExceptionCode;
import com.example.web.exception.WebException;
import com.example.web.gateway.Account;
import com.example.web.gateway.GatewayApi;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Optional;

@Stateless
public class AccountService implements Serializable {


    @Inject
    private GatewayApi gatewayApi;

    public Account getAccount(String token, String tenantId, BigInteger accountId) throws WebException {
        Optional<Account> accountOptional = gatewayApi.getAccount(token, tenantId, accountId);
        if (!accountOptional.isPresent()) {
            throw new WebException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        return accountOptional.get();
    }

}
