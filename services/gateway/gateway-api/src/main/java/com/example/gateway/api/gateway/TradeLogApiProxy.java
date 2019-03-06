package com.example.gateway.api.gateway;

import com.example.tradelog.api.spec.model.JournalModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@FeignClient(name = "tradelog-api", url = "localhost:8083/api/v1")
public interface TradeLogApiProxy {

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    ResponseEntity<List<JournalModel>> getAllByAccountId(@PathVariable("accountId") String accountId);
}
