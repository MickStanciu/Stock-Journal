package com.example.account.facade;

import com.example.account.exception.AccountException;
import com.example.account.exception.ExceptionCode;
import com.example.account.model.Account;
import com.example.account.service.AccountService;
import com.example.account.service.RoleService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class AccountFacade {

    private static int DEFAULT_ROLE_ID = 5;
    private static String DEFAULT_EMAIL = "not.set@domain.com";

    @Inject
    private AccountService accountService;

    @Inject
    private RoleService roleService;

    public Optional<Account> getAccount(String tenantId, String name, String password) {
        return accountService.getAccount(tenantId, name, password);
    }

    public Optional<Account> createAccount(String tenantId, String name, String password) throws AccountException {
        //todo: externalize Tenant as rest service

        //validate role
        //todo: find a way to obtain the lowest role.
//        Optional<Role> role = roleService.getRole(accountDto.getRoleId(), tenantId);
//        if (!role.isPresent()) {
//            throw new AccountException(ExceptionCode.ROLE_NOT_FOUND);
//        }

        //todo: add check if the account name already exists
        if (accountService.checkAccount(tenantId, name)) {
            throw new AccountException(ExceptionCode.ACCOUNT_EXISTS);
        }

        accountService.createAccount(tenantId, name, password, DEFAULT_EMAIL, DEFAULT_ROLE_ID);
        return accountService.getAccount(tenantId, name, password);
    }

}
