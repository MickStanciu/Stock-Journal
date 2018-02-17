package com.example.tenant.service;

import com.example.tenant.dao.TenantDao;
import com.example.tenant.model.Health;

import javax.ejb.Stateful;
import javax.inject.Inject;

@Stateful
public class HealthService {

    private Health health;

    @Inject
    private TenantDao tenantDao;

    public HealthService() {
        health = new Health();
        health.setFirstRecordOk(false);
    }

    public boolean isOk() {
        boolean firstRecordOk;
        try {
            firstRecordOk = tenantDao.checkFirstRecord();
        } catch (Exception e) {
            firstRecordOk = false;
        }
        health.setFirstRecordOk(firstRecordOk);
        return firstRecordOk;
    }

    public Health getHealth() {
        return health;
    }

}
