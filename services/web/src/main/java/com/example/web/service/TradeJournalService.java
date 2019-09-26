package com.example.web.service;

import com.example.gateway.api.model.DividendGWModel;
import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.gateway.api.model.ShareDataGWModel;
import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.gateway.api.model.TradeLogGWModel;
import com.example.gateway.api.model.TradeSummaryGWModel;
import com.example.gateway.api.model.TransactionSettingsGWModel;
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

    public void deleteShareTrade(String accountId, String transactionId) {
        tradeJournalGateway.deleteShareTrade(accountId, transactionId);
    }

    public void deleteOptionTrade(String accountId, String transactionId) {
        tradeJournalGateway.deleteOptionTrade(accountId, transactionId);
    }

    public DividendGWModel createDividendRecord(String accountId, DividendGWModel model) {
        return tradeJournalGateway.createDividendRecord(accountId, model);
    }

    public void deleteDividendRecord(String accountId, String transactionId) {
        tradeJournalGateway.deleteDividendRecord(accountId, transactionId);
    }

    public List<TradeSummaryGWModel> getSummary(String accountId) {
        return tradeJournalGateway.getSummary(accountId);
    }

    public ShareDataGWModel getShareData(String accountId, String symbol) {
        return tradeJournalGateway.getShareData(accountId, symbol);
    }

    public void updateSettings(String accountId, List<TransactionSettingsGWModel> transactionSettingsGWModelList) {
        tradeJournalGateway.updateSettings(accountId, transactionSettingsGWModelList);
    }

    public void updateSetting(String accountId, TransactionSettingsGWModel transactionSettingsGWModel) {
        tradeJournalGateway.updateSetting(accountId, transactionSettingsGWModel);
    }
}
