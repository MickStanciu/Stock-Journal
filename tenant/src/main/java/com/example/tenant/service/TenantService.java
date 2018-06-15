package com.example.tenant.service;

import com.example.tenant.model.TenantModel;
import com.example.tenant.repository.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TenantService {

    private static final Logger log = LoggerFactory.getLogger(TenantService.class);

    private TenantRepository tenantRepository;

    @Autowired
    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public TenantModel getTenant(String id) {
        return tenantRepository.getTenant(id);
    }
}
