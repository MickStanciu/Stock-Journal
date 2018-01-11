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

    public Optional<Account> getAccount(String name, String password, String tenantId) {
        return accountDao.getAccount(name, password, tenantId);
    }

    public boolean checkAccount(String name, String tenantId) {
        return accountDao.checkAccount(name, tenantId);
    }

    public void createAccount(AccountDto accountDto, String tenantId) {
        accountDao.createAccount(accountDto.getName(), accountDto.getPassword(), accountDto.getPassword(), tenantId, accountDto.getRoleId());
    }

}
