package com.example.gateway.api.resource;

import com.example.gateway.api.exception.ExceptionCode;
import com.example.gateway.api.exception.GatewayApiException;
import com.example.gateway.api.service.TradeLogService;
import com.example.tradelog.api.spec.model.JournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/tradelog", produces = "application/json")
public class TradeLogResource {

    private static final Logger log = LoggerFactory.getLogger(TradeLogResource.class);

    private TradeLogService tradeLogService;

    @Autowired
    public TradeLogResource(TradeLogService tradeLogService) {
        this.tradeLogService = tradeLogService;
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<List<JournalModel>> getAllByAccountId(@PathVariable(name = "accountId") String accountId) throws GatewayApiException {
        //todo validate input

        List<JournalModel> modelList = tradeLogService.getAllByAccountId(accountId);

        if (modelList == null || modelList.isEmpty()) {
            throw new GatewayApiException(ExceptionCode.TRADEJOURNAL_EMPTY);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(modelList);
    }
}
