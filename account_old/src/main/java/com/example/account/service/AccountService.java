package com.example.account.service;

import com.example.account.dao.AccountDao;
import com.example.account.model.AccountModel;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigInteger;
import java.util.List;

@Stateless
public class AccountService {

    private static final Logger log = Logger.getLogger(AccountService.class);

    @Inject
    private AccountDao accountDao;

    public AccountModel getAccount(String tenantId, String email, String password) {
        return accountDao.getAccount(tenantId, email, password);
    }

    public AccountModel getAccount(String tenantId, BigInteger accountId) {
        return accountDao.getAccount(tenantId, accountId);
    }

    public List<AccountModel> getAccountByRelationShip(String tenantId, BigInteger parentId, int depth) {
        return accountDao.getAccountsByRelationship(tenantId, parentId, depth);
    }

    public boolean checkAccount(String tenantId, String email) {
        return accountDao.checkAccount(tenantId, email);
    }

    public void createAccount(String tenantId, String name, String password, String email, int roleId) {
        accountDao.createAccount(tenantId, name, password, email, roleId);
    }

    public void updateAccount(String tenantId, BigInteger accountId, AccountModel originalAccount, AccountModel account) {
        //transfer only allowed fields
        AccountModel newAccount = copyAccount(tenantId, accountId, originalAccount, account);
        accountDao.updateAccount(tenantId, accountId, newAccount);
    }

    AccountModel copyAccount(String tenantId, BigInteger accountId, AccountModel originalAccount, AccountModel account) {
        AccountModel.Builder builder = new AccountModel.Builder();
        builder
            .havingPersonalDetails()
                .withTenantId(tenantId)
                .withId(accountId)
                .withName(account.getName() == null ? originalAccount.getName() : account.getName())
                .withEmail(account.getEmail() == null ? originalAccount.getEmail() : account.getEmail())
                .withPassword(account.getPassword() == null ? originalAccount.getPassword() : account.getPassword())
                .withFlagActive(account.isActive() == null ? originalAccount.isActive() : account.isActive())
            .havingRole()
                .withRole(account.getRole() == null ? originalAccount.getRole() : account.getRole());
        return builder.build();
    }
}
