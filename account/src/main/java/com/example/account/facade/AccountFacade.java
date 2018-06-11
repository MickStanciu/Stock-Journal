package com.example.account.facade;


import com.example.account.model.AccountModel;
import com.example.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountFacade {

    private AccountService accountService;

    @Autowired
    public AccountFacade(AccountService accountService) {
        this.accountService = accountService;
    }

    public Optional<AccountModel> getAccount(String tenantId, String email, String password) {
        AccountModel account = accountService.getAccount(tenantId, email, password);
//        addRoleInformation(account);
        return Optional.ofNullable(account);
    }
}
