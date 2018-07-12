package com.example.tenantapi.service;

import com.example.tenant.model.HealthModel;
import com.example.tenantapi.repository.TenantRepository;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class HealthService {

    private final HealthModel health;

    private TenantRepository tenantRepository;

    @Inject
    public HealthService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
        health = new HealthModel();
        health.setFirstRecordOk(false);
    }

    public boolean isOk() {
        boolean firstRecordOk;
        try {
            firstRecordOk = tenantRepository.checkFirstRecord();
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
