package com.example.tenant.service;

import com.example.tenant.dao.TenantDao;
import com.example.tenant.model.HealthModel;

import javax.ejb.Stateful;
import javax.inject.Inject;

@Stateful
public class HealthService {

    private HealthModel health;

    @Inject
    private TenantDao tenantDao;

    public HealthService() {
        health = new HealthModel();
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

    public HealthModel getHealth() {
        return health;
    }

}
