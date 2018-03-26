package com.example.gatewayapi.service;

import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.gateway.accountApi.Account;
import com.example.gatewayapi.gateway.accountApi.AccountGateway;
import com.example.gatewayapi.gateway.tenantApi.Tenant;
import com.example.gatewayapi.gateway.tenantApi.TenantGateway;
import com.example.common.security.TokenUtil;
import com.example.gatewayapi.model.AuthToken;
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


    public AuthToken getAuthResponse(String tenantId, String name, String password) throws GatewayApiException {
        Tenant tenant = getTenant(tenantId);
        Account account = getAccount(tenantId, name, password);

        String token = TokenUtil.generateToken(tenant.getId(), account.getName(), account.getRole().getName());

        return new AuthToken(token);
    }

    private Tenant getTenant(String tenantId) throws GatewayApiException {
        Optional<Tenant> tenantOptional = tenantGateway.getTenant(tenantId);
        if (!tenantOptional.isPresent()) {
            throw new GatewayApiException(ExceptionCode.TENANT_NOT_FOUND);
        }

        return tenantOptional.get();
    }

    private Account getAccount(String tenantId, String name, String password) throws GatewayApiException {
        Optional<Account> accountOptional = accountGateway.getAccount(tenantId, name, password);
        if (!accountOptional.isPresent()) {
            throw new GatewayApiException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        return accountOptional.get();
    }

}
