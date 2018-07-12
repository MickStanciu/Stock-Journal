//package com.example.tenantapi.service;
//
//import com.example.tenant.model.TenantModel;
//import com.example.tenant.repository.TenantRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//
//@Service
//public class TenantService {
//
//    private static final Logger log = LoggerFactory.getLogger(TenantService.class);
//
//    private TenantRepository tenantRepository;
//
//    @Autowired
//    public TenantService(TenantRepository tenantRepository) {
//        this.tenantRepository = tenantRepository;
//    }
//
//    public Optional<TenantModel> getTenant(String id) {
//        return Optional.ofNullable(tenantRepository.getTenant(id));
//    }
//}
