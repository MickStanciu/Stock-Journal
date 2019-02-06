package com.example.tenant.api.service;

import com.example.tenant.api.repository.TenantRepository;
import com.example.tenant.api.spec.model.TenantModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TenantService {

    private static final Logger log = LoggerFactory.getLogger(TenantService.class);

    private TenantRepository tenantRepository;

    @Autowired
    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public Optional<TenantModel> getTenant(String id) {
        try {
            return Optional.ofNullable(tenantRepository.getTenant(id));
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
            return Optional.empty();
        }
    }
}
