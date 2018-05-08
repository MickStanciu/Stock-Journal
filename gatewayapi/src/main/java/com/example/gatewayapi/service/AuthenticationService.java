package com.example.gatewayapi.service;

import com.example.account.model.AccountModel;
import com.example.common.security.TokenUtil;
import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.gateway.AccountGateway;
import com.example.gatewayapi.gateway.TenantGateway;
import com.example.gatewayapi.model.AuthToken;
import com.example.tenant.model.TenantModel;
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


    public AuthToken getAuthResponse(String tenantId, String email, String password) throws GatewayApiException {
        TenantModel tenant = getTenant(tenantId);
        AccountModel account = getAccount(tenantId, email, password);

        String token = TokenUtil.generateToken(tenant.getId(), account.getId(), account.getRole().getId());

        return new AuthToken(token);
    }

    private TenantModel getTenant(String tenantId) throws GatewayApiException {
        Optional<TenantModel> tenantOptional = tenantGateway.getTenant(tenantId);
        if (!tenantOptional.isPresent()) {
            throw new GatewayApiException(ExceptionCode.TENANT_NOT_FOUND);
        }

        return tenantOptional.get();
    }

    //todo: if grows, convert to façade and move this into AccountService
    private AccountModel getAccount(String tenantId, String email, String password) throws GatewayApiException {
        Optional<AccountModel> accountOptional = accountGateway.getAccount(tenantId, email, password);
        if (!accountOptional.isPresent()) {
            throw new GatewayApiException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        return accountOptional.get();
    }

}
