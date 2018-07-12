package com.example.tenantapi.service;

import com.example.tenant.model.HealthModel;
import com.example.tenantapi.repository.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class HealthService {

    private static final Logger log = LoggerFactory.getLogger(HealthService.class);

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
        } catch (Exception ex) {
            log.error(ex.getMessage());
            firstRecordOk = false;
        }
        health.setFirstRecordOk(firstRecordOk);
        return firstRecordOk;
    }

    public HealthModel getHealth() {
        return health;
    }

}
