package com.example.web.service;

import com.example.gateway.api.model.*;
import com.example.web.gateway.TradeJournalGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeJournalService {

    private TradeJournalGateway tradeJournalGateway;

    public TradeJournalService(TradeJournalGateway tradeJournalGateway) {
        this.tradeJournalGateway = tradeJournalGateway;
    }

    public TradeLogGWModel getAllTradesBySymbol(String accountId, String symbol) {
        return tradeJournalGateway.getAllTradesBySymbol(accountId, symbol);
    }

    public List<String> getUniqueSymbols(String accountId) {
        return tradeJournalGateway.getUniqueSymbols(accountId);
    }

    public ShareJournalGWModel createShareTrade(String accountId, ShareJournalGWModel model) {
        return tradeJournalGateway.createShareTrade(accountId, model);
    }

    public OptionJournalGWModel createOptionTrade(String accountId, OptionJournalGWModel model) {
        return tradeJournalGateway.createOptionTrade(accountId, model);
    }

    public void deleteShareTrade(String accountId, String symbol, String transactionId) {
        tradeJournalGateway.deleteShareTrade(accountId, symbol, transactionId);
    }

    public void deleteOptionTrade(String accountId, String symbol, String transactionId) {
        tradeJournalGateway.deleteOptionTrade(accountId, symbol, transactionId);
    }

    public List<TradeSummaryGWModel> getSummary(String accountId) {
        return tradeJournalGateway.getSummary(accountId);
    }

    public ShareDataGWModel getShareData(String accountId, String symbol) {
        return tradeJournalGateway.getShareData(accountId, symbol);
    }
}
