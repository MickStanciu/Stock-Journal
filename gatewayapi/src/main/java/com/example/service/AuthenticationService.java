package com.example.service;

import com.example.exception.ExceptionCode;
import com.example.exception.GatewayApiException;
import com.example.gateway.Account;
import com.example.gateway.AccountGateway;
import com.example.gateway.Tenant;
import com.example.gateway.TenantGateway;
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


    public void getAccountDetails(String tenantId, String name, String password) throws GatewayApiException {
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
