package com.example.gateway.api.gateway;

import com.example.tenant.api.spec.model.TenantModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TenantGateway {

    private static final Logger log = LoggerFactory.getLogger(TenantGateway.class);

    @Value("gateway.tenant.address")
    private String SERVICE_URL;

    private TenantApiProxy tenantApiProxy;

    @Autowired
    public TenantGateway(TenantApiProxy tenantApiProxy) {
        this.tenantApiProxy = tenantApiProxy;
    }

    public Optional<TenantModel> getTenant(String tenantId) {
        ResponseEntity<TenantModel> responseEntity = tenantApiProxy.tenantByUUID(tenantId);
        return Optional.ofNullable(responseEntity.getBody());
    }
}
