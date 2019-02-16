package com.example.tenant.api.service;

import com.example.tenant.api.repository.TenantRepository;
import com.example.tenant.api.spec.model.HealthModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HealthService {

    private static final Logger log = LoggerFactory.getLogger(HealthService.class);

    private final HealthModel health;

    private TenantRepository tenantRepository;

    @Autowired
    public HealthService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
        health = new HealthModel();
        health.setFirstRecordOk(false);
    }

    boolean isOk() {
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
