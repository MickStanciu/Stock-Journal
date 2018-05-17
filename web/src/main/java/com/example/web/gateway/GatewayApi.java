package com.example.web.gateway;

import com.example.account.model.AccountModel;
import com.example.core.model.TimeSheetEntryModel;
import com.example.gatewayapi.model.AuthTokenModel;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GatewayApi {

    AuthTokenModel authenticate(String tenantId, String email, String password);
    Optional<AccountModel> getAccount(String token, BigInteger accountId);
    List<TimeSheetEntryModel> getEntries(String token, BigInteger accountId, LocalDateTime fromDate, LocalDateTime toDate);
}
