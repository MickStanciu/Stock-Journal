package com.example.account.service;

import com.example.account.dao.AccountDao;
import com.example.account.model.HealthModel;

import javax.ejb.Stateful;
import javax.inject.Inject;

@Stateful
public class HealthService {

    private HealthModel health;

    @Inject
    private AccountDao accountDao;

    public HealthService() {
        health = new HealthModel();
        health.setFirstRecordOk(false);
    }

    public boolean isOk() {
        boolean firstRecordOk;
        try {
            firstRecordOk = accountDao.checkFirstRecord();
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
