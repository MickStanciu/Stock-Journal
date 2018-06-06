package com.example.account.service;

import com.example.account.model.HealthModel;
import com.example.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    private final HealthModel health;

    private AccountRepository accountRepository;

    @Autowired
    public HealthService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        health = new HealthModel();
        health.setFirstRecordOk(false);
    }

    public boolean isOk() {
        boolean firstRecordOk;
        try {
            firstRecordOk = accountRepository.checkFirstRecord();
        } catch (Exception e) {
            firstRecordOk = false;
        }
        health.setFirstRecordOk(firstRecordOk);
        return firstRecordOk;
    }

    public HealthModel getHealth() {
        return health;
    }
}
