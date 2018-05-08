package com.example.tenant.service;

import com.example.tenant.dao.TenantDao;
import com.example.tenant.model.TenantModel;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class TenantService {

    private static final Logger log = Logger.getLogger(TenantService.class);

    @Inject
    private TenantDao tenantDao;

    public Optional<TenantModel> getTenant(String id) {
        //todo: add validation for id
        return tenantDao.getTenant(id);
    }
}
