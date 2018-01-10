package com.example.account.facade;

import com.example.account.exception.AccountException;
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
        Optional<Role> role = roleService.getRole(accountDto.getRoleId(), tenantId);
        if (role.isPresent()) {

        }
        return Optional.empty();
    }

}
