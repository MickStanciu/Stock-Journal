package com.example.gateway.api.service;

import com.example.account.api.spec.model.AccountModel;
import com.example.gateway.api.gateway.AccountGateway;
import com.example.gateway.api.gateway.TenantGateway;
import com.example.gateway.api.model.AuthTokenModel;
import com.example.gateway.api.security.TokenUtil;
import com.example.tenant.api.spec.model.TenantModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private AccountGateway accountGateway;
    private TenantGateway tenantGateway;

    @Autowired
    public AuthenticationService(AccountGateway accountGateway, TenantGateway tenantGateway) {
        this.accountGateway = accountGateway;
        this.tenantGateway = tenantGateway;
    }

    public Optional<AuthTokenModel> getAuthResponse(String tenantId, String email, String password) {
        Optional<TenantModel> tenantOptional = tenantGateway.getTenant(tenantId);
        Optional<AccountModel> accountOptional = accountGateway.getAccount(tenantId, email, password);

        if (!tenantOptional.isPresent() || !accountOptional.isPresent()) {
            return Optional.empty();
        }

        TenantModel tenant = tenantOptional.get();
        AccountModel account = accountOptional.get();

        String token = TokenUtil.generateToken(tenant.getId(), account.getId(), account.getRole().getId());

        return Optional.of(new AuthTokenModel(token));
    }
}
