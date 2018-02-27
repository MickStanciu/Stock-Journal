package com.example.gatewayapi.service;

import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.gateway.accountApi.Account;
import com.example.gatewayapi.gateway.accountApi.AccountGateway;
import com.example.gatewayapi.gateway.tenantApi.Tenant;
import com.example.gatewayapi.gateway.tenantApi.TenantGateway;
import com.example.gatewayapi.security.TokenUtil;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class AuthenticationService {

    private static final Logger log = Logger.getLogger(AuthenticationService.class);

    @Inject
    private TenantGateway tenantGateway;

    @Inject
    private AccountGateway accountGateway;


    public String authenticate(String tenantId, String name, String password) throws GatewayApiException {
        Optional<Tenant> tenantOptional = getTenant(tenantId);
        if (!tenantOptional.isPresent()) {
            throw new GatewayApiException(ExceptionCode.TENANT_NOT_FOUND);
        }
        Tenant tenant = tenantOptional.get();

        Optional<Account> accountOptional = getAccount(tenantId, name, password);
        if (!accountOptional.isPresent()) {
            throw new GatewayApiException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }
        Account account = accountOptional.get();

        return TokenUtil.generateToken(tenant.getId(), account.getName(), account.getRole().getName());
    }

    //todo: move it out if this class grows
    private Optional<Tenant> getTenant(String tenantId) {
        try {
            return Optional.of(tenantGateway.getTenant(tenantId));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return Optional.empty();
        }
    }

    private Optional<Account> getAccount(String tenantId, String name, String password) {
        try {
            return Optional.of(accountGateway.getAccount(tenantId, name, password));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return Optional.empty();
        }
    }
}
