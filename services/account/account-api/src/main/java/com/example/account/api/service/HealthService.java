package com.example.account.api.service;

import com.example.account.api.repository.AccountRepository;
import com.example.account.api.spec.model.HealthModel;
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

    private boolean isOk() {
        boolean firstRecordOk;
        try {
            firstRecordOk = accountRepository.checkFirstRecord();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            firstRecordOk = false;
        }
        health.setFirstRecordOk(firstRecordOk);
        return firstRecordOk;
    }

    public HealthModel getHealth() {
        isOk();
        return health;
    }
}
