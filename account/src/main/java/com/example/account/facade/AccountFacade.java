package com.example.account.facade;

import com.example.account.exception.AccountException;
import com.example.account.exception.ExceptionCode;
import com.example.account.model.AccountModel;
import com.example.account.model.RoleModel;
import com.example.account.service.AccountService;
import com.example.account.service.RoleService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Stateless
public class AccountFacade {

    private static final int DEFAULT_ROLE_ID = 2;
    private static String DEFAULT_EMAIL = "not.set@domain.com";

    @Inject
    private AccountService accountService;

    @Inject
    private RoleService roleService;

    public Optional<AccountModel> getAccount(String tenantId, String email, String password) {
        AccountModel account = accountService.getAccount(tenantId, email, password);
        addRoleInformation(account);
        return Optional.ofNullable(account);
    }

    public Optional<AccountModel> getAccount(String tenantId, BigInteger accountId) {
        AccountModel account = accountService.getAccount(tenantId, accountId);
        addRoleInformation(account);
        return Optional.ofNullable(account);
    }

    public List<AccountModel> getAccountsByRelationship(String tenantId, BigInteger parentId, int depth) {
        List<AccountModel> accountList = accountService.getAccountByRelationShip(tenantId, parentId, depth);
        for(AccountModel account : accountList) {
            addRoleInformation(account);
        }
        return accountList;
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

    public Optional<AccountModel> createAccount(String tenantId, String email, String password) throws AccountException {
        if (accountService.checkAccount(tenantId, email)) {
            throw new AccountException(ExceptionCode.ACCOUNT_EXISTS);
        }

        accountService.createAccount(tenantId, email, password, DEFAULT_EMAIL, DEFAULT_ROLE_ID);
        return Optional.ofNullable(accountService.getAccount(tenantId, email, password));
    }

    public Optional<AccountModel> updateAccount(String tenantId, BigInteger accountId, AccountModel newAccount) throws AccountException {
        AccountModel originalAccount = accountService.getAccount(tenantId, accountId);

        if (originalAccount == null) {
            throw new AccountException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        if (newAccount.getName() != null && !newAccount.getName().equals(originalAccount.getName()) && accountService.checkAccount(tenantId, newAccount.getName())) {
            throw new AccountException(ExceptionCode.ACCOUNT_NAME_EXISTS);
        }

        //validate role
        if (newAccount.getRole() != null && newAccount.getRole().getId() != null) {
            RoleModel role = roleService.getRole(tenantId, newAccount.getRole().getId());
            if (role == null) {
                throw new AccountException(ExceptionCode.ROLE_NOT_FOUND);
            }
        }

        accountService.updateAccount(tenantId, accountId, originalAccount, newAccount);
        return Optional.ofNullable(accountService.getAccount(tenantId, accountId));
    }
}
