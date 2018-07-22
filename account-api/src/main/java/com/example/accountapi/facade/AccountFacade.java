package com.example.accountapi.facade;


import com.example.account.model.AccountModel;
import com.example.account.model.RoleModel;
import com.example.accountapi.exception.AccountException;
import com.example.accountapi.service.AccountService;
import com.example.accountapi.service.RoleService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class AccountFacade {

    private static final int DEFAULT_ROLE_ID = 1;
    private static final String DEFAULT_NAME = "not set";

    private AccountService accountService;
    private RoleService roleService;

    @Inject
    public AccountFacade(AccountService accountService, RoleService roleService) {
        this.accountService = accountService;
        this.roleService = roleService;
    }

    public Optional<AccountModel> getAccount(String tenantId, String email, String password) {
        Optional<AccountModel> accountOptional = accountService.getAccount(tenantId, email, password);
        accountOptional.ifPresent(this::addRoleInformation);
        return accountOptional;
    }

    public Optional<AccountModel> getAccount(String tenantId, long accountId) {
        Optional<AccountModel> accountOptional = accountService.getAccount(tenantId, accountId);
        accountOptional.ifPresent(this::addRoleInformation);
        return accountOptional;
    }

    public List<AccountModel> getAccountsByRelationship(String tenantId, long parentId, int depth) {
        List<AccountModel> accountList = accountService.getAccountByRelationShip(tenantId, parentId, depth);
        for(AccountModel account : accountList) {
            addRoleInformation(account);
        }
        return accountList;
    }

    public Optional<AccountModel> createAccount(String tenantId, String name, String email, String password) throws AccountException {
//        if (accountService.checkAccount(tenantId, email)) {
//            throw new AccountException(ExceptionCode.ACCOUNT_EXISTS);
//        }
//
//        accountService.createAccount(tenantId, DEFAULT_NAME, password, email, DEFAULT_ROLE_ID);
//        return Optional.ofNullable(accountService.getAccount(tenantId, email, password));
        return Optional.empty();
    }

    public Optional<AccountModel> updateAccount(String tenantId, long accountId, AccountModel newAccount) throws AccountException {
//        AccountModel originalAccount = accountService.getAccount(tenantId, accountId);
//
//        if (originalAccount == null) {
//            throw new AccountException(ExceptionCode.ACCOUNT_NOT_FOUND);
//        }
//
//        if (newAccount.getName() != null && !newAccount.getName().equals(originalAccount.getName()) && accountService.checkAccount(tenantId, newAccount.getName())) {
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
//        accountService.updateAccount(tenantId, accountId, originalAccount, newAccount);
//        return Optional.ofNullable(accountService.getAccount(tenantId, accountId));
        return Optional.empty();
    }

    private void addRoleInformation(AccountModel account) {
        if (account == null) {
            return;
        }

        RoleModel role = roleService.getRole(account.getTenantId(), account.getRoleId());
        if (role != null) {
            AccountModel.builder(account).havingRole().withRole(role);
        }
    }
}
