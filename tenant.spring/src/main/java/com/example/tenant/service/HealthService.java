package com.example.tenant.service;

import com.example.tenant.model.HealthModel;
import com.example.tenant.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HealthService {

    private final HealthModel health;

    private TenantRepository tenantRepository;

    @Autowired
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
