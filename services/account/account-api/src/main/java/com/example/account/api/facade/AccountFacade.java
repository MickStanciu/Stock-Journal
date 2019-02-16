package com.example.account.api.facade;


import com.example.account.api.service.AccountService;
import com.example.account.api.spec.model.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountFacade {

    private static final int DEFAULT_ROLE_ID = 1;
    private static final String DEFAULT_NAME = "not set";

    private AccountService accountService;

    @Autowired
    public AccountFacade(AccountService accountService) {
        this.accountService = accountService;
    }

    public Optional<AccountModel> getAccount(String email, String password) {
        return accountService.getAccount(email, password);
    }

    public Optional<AccountModel> getAccount(String accountId) {
        return accountService.getAccount(accountId);
    }

//    public Optional<AccountModel> createAccount(String tenantId, String name, String email, String password) throws AccountException {
//        if (accountService.checkAccount(tenantId, email)) {
//            throw new AccountException(ExceptionCode.ACCOUNT_EXISTS);
//        }
//        return accountService.getAccount(tenantId, email, password);
//    }

//    public Optional<AccountModel> updateAccount(String tenantId, long accountId, AccountModel newAccount) throws AccountException {
//        Optional<AccountModel> optionalAccountModel = accountService.getAccount(tenantId, accountId);
//
//        if (!optionalAccountModel.isPresent()) {
//            throw new AccountException(ExceptionCode.ACCOUNT_NOT_FOUND);
//        }
//        AccountModel accountModel = optionalAccountModel.get();
//
//        if (newAccount.getName() != null && !newAccount.getName().equals(accountModel.getName()) && accountService.checkAccount(tenantId, newAccount.getName())) {
//            throw new AccountException(ExceptionCode.ACCOUNT_NAME_EXISTS);
//        }
//
//        //validate role
//        if (newAccount.getRole() != null && newAccount.getRole().getId() != null) {
//            RoleModel role = roleService.getRole(tenantId, newAccount.getRole().getId());
//            if (role == null) {
//                throw new AccountException(ExceptionCode.ROLE_NOT_FOUND);
//            }
//        }
//
//        accountService.updateAccount(tenantId, accountId, accountModel, newAccount);
//        return accountService.getAccount(tenantId, accountId);
//    }
}
