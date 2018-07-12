package com.example.tenantapi.service;

import com.example.tenant.model.TenantModel;
import com.example.tenantapi.repository.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;


@Singleton
public class TenantService {

    private static final Logger log = LoggerFactory.getLogger(TenantService.class);

    private TenantRepository tenantRepository;

    @Inject
    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public Optional<TenantModel> getTenant(String id) {
        try {
            return Optional.ofNullable(tenantRepository.getTenant(id));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return Optional.empty();
        }

    }
}
