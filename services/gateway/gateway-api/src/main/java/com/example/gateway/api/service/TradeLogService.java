package com.example.gateway.api.service;

import com.example.gateway.api.gateway.TradeLogGateway;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeLogService {

    private static final Logger log = LoggerFactory.getLogger(TradeLogService.class);

    private TradeLogGateway tradeLogGateway;

    @Autowired
    public TradeLogService(TradeLogGateway tradeLogGateway) {
        this.tradeLogGateway = tradeLogGateway;
    }

    public List<OptionJournalModel> getAllByAccountId(String accountId) {
        return tradeLogGateway.getAllByAccountId(accountId);
    }
}
