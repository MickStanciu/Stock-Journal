package com.example.gateway.api.service;

import com.example.account.api.spec.model.AccountModel;
import com.example.gateway.api.gateway.AccountGateway;
import com.example.gateway.api.security.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private AccountGateway accountGateway;

    public AuthenticationService(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public Optional<AuthTokenModel> getAuthResponse(String email, String password) {
        Optional<AccountModel> accountOptional = accountGateway.getAccount(email, password);

        if (accountOptional.isEmpty()) {
            return Optional.empty();
        }

        AccountModel account = accountOptional.get();
        String token = TokenUtil.generateToken(account.getId(), 0);

        return Optional.of(new AuthTokenModel(token));
    }
}
