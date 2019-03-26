package com.example.web.service;

import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.web.gateway.TradeJournalGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeJournalService {

    private TradeJournalGateway tradeJournalGateway;

    @Autowired
    public TradeJournalService(TradeJournalGateway tradeJournalGateway) {
        this.tradeJournalGateway = tradeJournalGateway;
    }

    public List<OptionJournalGWModel> getAllByAccountId(String accountId) {
        return tradeJournalGateway.getAllByAccountId(accountId);
    }
}
