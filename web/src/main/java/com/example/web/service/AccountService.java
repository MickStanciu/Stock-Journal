package com.example.web.service;

import com.example.account.model.AccountModel;
import com.example.web.exception.ExceptionCode;
import com.example.web.exception.WebException;
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

    public AccountModel getAccount(String token, BigInteger accountId) throws WebException {
        Optional<AccountModel> accountOptional = gatewayApi.getAccount(token, accountId);
        if (!accountOptional.isPresent()) {
            throw new WebException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        return accountOptional.get();
    }

}
