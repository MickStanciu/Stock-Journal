package com.example.account.service;

import com.example.account.dao.AccountDao;
import com.example.account.model.Health;

import javax.ejb.Stateful;
import javax.inject.Inject;

@Stateful
public class HealthService {

    private Health health;

    @Inject
    private AccountDao accountDao;

    public HealthService() {
        health = new Health();
        health.setFirstRecordOk(false);
    }

    public boolean isOk() {
        boolean firstRecordOk = accountDao.checkFirstRecord();
        health.setFirstRecordOk(true);
        return firstRecordOk;
    }

    public Health getHealth() {
        return health;
    }

}
