package com.example.account.api.service;

import com.example.account.api.repository.AccountRepository;
import com.example.account.api.spec.model.AccountModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<AccountModel> getAccount(String tenantId, String email, String password) {
        try {
            return Optional.ofNullable(accountRepository.getAccount(tenantId, email, password));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return Optional.empty();
        }
    }

    public Optional<AccountModel> getAccount(String tenantId, long accountId) {
        try {
            return Optional.ofNullable(accountRepository.getAccount(tenantId, accountId));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return Optional.empty();
        }
    }

    public List<AccountModel> getAccountByRelationShip(String tenantId, long parentId, int depth) {
        return accountRepository.getAccountsByRelationship(tenantId, parentId, depth);
    }

    public boolean checkAccount(String tenantId, String email) {
        return accountRepository.checkAccount(tenantId, email);
    }

    public void createAccount(String tenantId, String name, String password, String email, int roleId) {
        accountRepository.createAccount(tenantId, name, password, email, roleId);
    }

    public void updateAccount(String tenantId, long accountId, AccountModel originalAccount, AccountModel account) {
        //transfer only allowed fields
        AccountModel newAccount = copyAccount(tenantId, accountId, originalAccount, account);
        accountRepository.updateAccount(tenantId, accountId, newAccount);
    }

    AccountModel copyAccount(String tenantId, long accountId, AccountModel originalAccount, AccountModel account) {
        AccountModel.Builder builder = new AccountModel.Builder();
        builder
                .havingPersonalDetails()
                .withTenantId(tenantId)
                .withId(accountId)
                .withName(account.getName() == null ? originalAccount.getName() : account.getName())
                .withEmail(account.getEmail() == null ? originalAccount.getEmail() : account.getEmail())
                .withPassword(account.getPassword() == null ? originalAccount.getPassword() : account.getPassword())
                .withFlagActive(account.isActive() ? originalAccount.isActive() : account.isActive())
                .havingRole()
                .withRole(account.getRole() == null ? originalAccount.getRole() : account.getRole());
        return builder.build();
    }

}
