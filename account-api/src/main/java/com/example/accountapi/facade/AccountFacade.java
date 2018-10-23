package com.example.accountapi.facade;


import com.example.accountapi.exception.AccountException;
import com.example.accountapi.exception.ExceptionCode;
import com.example.accountapi.model.AccountModel;
import com.example.accountapi.model.RoleModel;
import com.example.accountapi.service.AccountService;
import com.example.accountapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountFacade {

    private static final int DEFAULT_ROLE_ID = 1;
    private static final String DEFAULT_NAME = "not set";

    private AccountService accountService;
    private RoleService roleService;

    @Autowired
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
        if (accountService.checkAccount(tenantId, email)) {
            throw new AccountException(ExceptionCode.ACCOUNT_EXISTS);
        }
        return accountService.getAccount(tenantId, email, password);
    }

    public Optional<AccountModel> updateAccount(String tenantId, long accountId, AccountModel newAccount) throws AccountException {
        Optional<AccountModel> optionalAccountModel = accountService.getAccount(tenantId, accountId);

        if (!optionalAccountModel.isPresent()) {
            throw new AccountException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }
        AccountModel accountModel = optionalAccountModel.get();

        if (newAccount.getName() != null && !newAccount.getName().equals(accountModel.getName()) && accountService.checkAccount(tenantId, newAccount.getName())) {
            throw new AccountException(ExceptionCode.ACCOUNT_NAME_EXISTS);
        }

        //validate role
        if (newAccount.getRole() != null && newAccount.getRole().getId() != null) {
            RoleModel role = roleService.getRole(tenantId, newAccount.getRole().getId());
            if (role == null) {
                throw new AccountException(ExceptionCode.ROLE_NOT_FOUND);
            }
        }

        accountService.updateAccount(tenantId, accountId, accountModel, newAccount);
        return accountService.getAccount(tenantId, accountId);
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
