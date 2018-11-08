package com.example.gatewayapi.service;

import com.example.accountapi.model.AccountModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

//    private AccountGateway accountGateway;

//    @Autowired
//    public AccountService(AccountGateway accountGateway) {
//        this.accountGateway = accountGateway;
//    }


    public Optional<AccountModel> getAccount(String tenantId, BigInteger accountId) {
        return accountGateway.getAccount(tenantId, accountId);
    }
}
