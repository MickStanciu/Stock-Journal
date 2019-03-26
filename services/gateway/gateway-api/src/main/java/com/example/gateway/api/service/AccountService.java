package com.example.gateway.api.service;

import com.example.account.api.spec.model.AccountModel;
import com.example.gateway.api.gateway.AccountGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private AccountGateway accountGateway;

    public AccountService(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }


    public Optional<AccountModel> getAccount(String accountId) {
        return accountGateway.getAccount(accountId);
    }
}
