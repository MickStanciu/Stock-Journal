package com.example.account.service;

import com.example.account.dao.AccountDao;
import com.example.account.model.Account;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigInteger;
import java.util.Optional;

@Stateless
public class AccountService {

    private static final Logger log = Logger.getLogger(AccountService.class);

    @Inject
    private AccountDao accountDao;

    public Optional<Account> getAccount(String tenantId, String name, String password) {
        return accountDao.getAccount(tenantId, name, password);
    }

    public Optional<Account> getAccount(String tenantId, BigInteger accountId) {
        return accountDao.getAccount(tenantId, accountId);
    }

    public boolean checkAccount(String tenantId, String name) {
        return accountDao.checkAccount(tenantId, name);
    }

    public void createAccount(String tenantId, String name, String password, String email, int roleId) {
        accountDao.createAccount(tenantId, name, password, email, roleId);
    }

    public void updateAccount(String tenantId, BigInteger accountId, Account originalAccount, Account account) {
        //transfer only allowed fields
        Account newAccount = copyAccount(tenantId, accountId, originalAccount, account);

    }

    protected Account copyAccount(String tenantId, BigInteger accountId, Account originalAccount, Account account) {
        return new Account.Builder()
                .withTenantId(tenantId)
                .withId(accountId)

                .withEmail(account.getEmail() == null ? originalAccount.getEmail() : account.getEmail())
                .withName(account.getName() == null ? originalAccount.getName() : account.getName())
                .withPassword(account.getPassword() == null ? originalAccount.getPassword() : account.getPassword())

                .withRole(originalAccount.getRole())
                .withFlagActive(originalAccount.isActive())

                .build();
    }
}
