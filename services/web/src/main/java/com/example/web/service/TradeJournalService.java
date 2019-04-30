package com.example.web.service;

import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.gateway.api.model.TradeLogModelGW;
import com.example.web.gateway.TradeJournalGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeJournalService {

    private TradeJournalGateway tradeJournalGateway;

    public TradeJournalService(TradeJournalGateway tradeJournalGateway) {
        this.tradeJournalGateway = tradeJournalGateway;
    }

//    public List<OptionJournalGWModel> getAllByAccountId(String accountId) {
//        return tradeJournalGateway.getAllByAccountId(accountId);
//    }

    public TradeLogModelGW getAllTradesByAndSymbol(String accountId, String symbol) {
        return tradeJournalGateway.getAllTradesByAndSymbol(accountId, symbol);
    }

    public List<String> getUniqueSymbols(String accountId) {
        return tradeJournalGateway.getUniqueSymbols(accountId);
    }
}
