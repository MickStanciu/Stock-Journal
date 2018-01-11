package com.example.account.facade;

import com.example.account.exception.AccountException;
import com.example.account.exception.ExceptionCode;
import com.example.account.model.request.AccountDto;
import com.example.account.model.response.Account;
import com.example.account.model.response.Role;
import com.example.account.service.AccountService;
import com.example.account.service.RoleService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class AccountFacade {

    @Inject
    private AccountService accountService;

    @Inject
    private RoleService roleService;

    public Optional<Account> getAccount(String name, String password, String tenantId) {
        return accountService.getAccount(name, password, tenantId);
    }

    public Optional<Account> createAccount(AccountDto accountDto, String tenantId) throws AccountException {
        //todo: externalize Tenant as rest service

        //validate role
        Optional<Role> role = roleService.getRole(accountDto.getRoleId(), tenantId);
        if (!role.isPresent()) {
            throw new AccountException(ExceptionCode.ROLE_NOT_FOUND);
        }

        //todo: add check if the account name already exists
        if (accountService.checkAccount(accountDto.getName(), tenantId)) {
            throw new AccountException(ExceptionCode.ACCOUNT_EXISTS);
        }

        accountService.createAccount(accountDto, tenantId);

        return accountService.getAccount(accountDto.getName(), accountDto.getPassword(), tenantId);
    }

}