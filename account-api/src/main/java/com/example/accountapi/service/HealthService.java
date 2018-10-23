package com.example.accountapi.service;

import com.example.accountapi.model.HealthModel;
import com.example.accountapi.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HealthService {

    private static final Logger log = LoggerFactory.getLogger(HealthService.class);

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
