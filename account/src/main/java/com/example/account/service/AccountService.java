package com.example.account.service;

import com.example.account.dao.AccountDao;
import com.example.account.model.request.AccountDto;
import com.example.account.model.response.Account;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class AccountService {

    private static final Logger log = Logger.getLogger(AccountService.class);

    @Inject
    private AccountDao accountDao;

    public Optional<Account> getAccount(String tenantId, String name, String password) {
        return accountDao.getAccount(tenantId, name, password);
    }

    public boolean checkAccount(String tenantId, String name) {
        return accountDao.checkAccount(tenantId, name);
    }

    public void createAccount(String tenantId, String name, String password, String email, int roleId) {
        accountDao.createAccount(tenantId, name, password, email, roleId);
    }

}
