package com.example.gateway.api.gateway;

import com.example.account.api.spec.model.AccountModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "tradelog-api", url = "localhost:8083/api/v1")
public interface TradeLogApiProxy {

    @RequestMapping(value = "/{tenantId}", method = RequestMethod.GET)
    ResponseEntity<AccountModel> tradelogBy(
            @PathVariable(name = "tenantId") String tenantId,
            @RequestParam(name = "email", defaultValue = "") String email,
            @RequestParam(name = "password", defaultValue = "") String password
    );
}
