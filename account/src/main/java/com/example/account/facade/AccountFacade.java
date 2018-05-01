package com.example.account.facade;

import com.example.account.exception.AccountException;
import com.example.account.exception.ExceptionCode;
import com.example.account.model.Account;
import com.example.account.model.Role;
import com.example.account.service.AccountService;
import com.example.account.service.RoleService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigInteger;
import java.util.Optional;

@Stateless
public class AccountFacade {

    private static int DEFAULT_ROLE_ID = 5;
    private static String DEFAULT_EMAIL = "not.set@domain.com";

    @Inject
    private AccountService accountService;

    @Inject
    private RoleService roleService;

    public Optional<Account> getAccount(String tenantId, String email, String password) {
        return accountService.getAccount(tenantId, email, password);
    }

    public Optional<Account> getAccount(String tenantId, BigInteger accountId) {
        Optional<Account> accountOptional = accountService.getAccount(tenantId, accountId);

        if (accountOptional.isPresent()) {
            Optional<Role> roleOptional = roleService.getRole(tenantId, accountOptional.get().getRole().getId());
            if (roleOptional.isPresent()) {
//                accountOptional.get().
            }
        }

        return accountService.getAccount(tenantId, accountId);
    }

    public Optional<Account> createAccount(String tenantId, String email, String password) throws AccountException {
        if (accountService.checkAccount(tenantId, email)) {
            throw new AccountException(ExceptionCode.ACCOUNT_EXISTS);
        }

        accountService.createAccount(tenantId, email, password, DEFAULT_EMAIL, DEFAULT_ROLE_ID);
        return accountService.getAccount(tenantId, email, password);
    }

    public Optional<Account> updateAccount(String tenantId, BigInteger accountId, Account newAccount) throws AccountException {
        Optional<Account> originalAccount = accountService.getAccount(tenantId, accountId);

        if (!originalAccount.isPresent()) {
            throw new AccountException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        if (newAccount.getName() != null && !newAccount.getName().equals(originalAccount.get().getName()) && accountService.checkAccount(tenantId, newAccount.getName())) {
            throw new AccountException(ExceptionCode.ACCOUNT_NAME_EXISTS);
        }

        //validate role
        if (newAccount.getRole() != null && newAccount.getRole().getId() != null) {
            Optional<Role> role = roleService.getRole(tenantId, newAccount.getRole().getId());
            if (!role.isPresent()) {
                throw new AccountException(ExceptionCode.ROLE_NOT_FOUND);
            }
        }

        accountService.updateAccount(tenantId, accountId, originalAccount.get(), newAccount);
        return accountService.getAccount(tenantId, accountId);
    }
}
