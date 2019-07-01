package com.example.web.service;

import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.gateway.api.model.TradeLogModelGW;
import com.example.web.converter.SyntheticSharesGenerator;
import com.example.web.converter.TradeLogModelConverter;
import com.example.web.gateway.TradeJournalGateway;
import com.example.web.model.TradeLogModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeJournalService {

    private TradeJournalGateway tradeJournalGateway;

    public TradeJournalService(TradeJournalGateway tradeJournalGateway) {
        this.tradeJournalGateway = tradeJournalGateway;
    }

    public TradeLogModel getAllTradesBySymbol(String accountId, String symbol) {
        TradeLogModelGW tradeLogModelGW = tradeJournalGateway.getAllTradesBySymbol(accountId, symbol);
        TradeLogModel tradeLogModel = new TradeLogModelConverter().apply(tradeLogModelGW);
        tradeLogModel.setSyntheticShareList(new SyntheticSharesGenerator().apply(tradeLogModelGW.getShareList()));
        return tradeLogModel;
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
}
