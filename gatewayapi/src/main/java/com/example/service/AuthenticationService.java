package com.example.service;

import com.example.exception.ExceptionCode;
import com.example.exception.GatewayApiException;
import com.example.gateway.Tenant;
import com.example.gateway.TenantGateway;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AuthenticationService {

    private static final Logger log = Logger.getLogger(AuthenticationService.class);

    @Inject
    private TenantGateway tenantGateway;


    public void getAccountDetails(String tenantId, String name, String password) throws GatewayApiException {
        Tenant tenant = null;
        try {
            tenant = tenantGateway.getTenant(tenantId);
            log.info(tenant.getId());
            log.info(tenant.getName());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new GatewayApiException(ExceptionCode.TENANT_NOT_FOUND);
        }
    }
}
