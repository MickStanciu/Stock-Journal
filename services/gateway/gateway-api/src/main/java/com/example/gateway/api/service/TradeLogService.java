package com.example.gateway.api.service;

import com.example.gateway.api.converter.*;
import com.example.gateway.api.gateway.TradeLogGateway;
import com.example.gateway.api.model.*;
import com.example.tradelog.api.spec.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class TradeLogService {

    private static final Logger log = LoggerFactory.getLogger(TradeLogService.class);

    private TradeLogGateway tradeLogGateway;

    public TradeLogService(TradeLogGateway tradeLogGateway) {
        this.tradeLogGateway = tradeLogGateway;
    }

    public TradeLogModel getAllBySymbol(String accountId, String symbol) throws ExecutionException, InterruptedException {
        CompletableFuture<List<ShareJournalModel>> futureShareList = tradeLogGateway.getShareTransactionsBySymbol(accountId, symbol);
        CompletableFuture<List<OptionJournalModel>> futureOptionList = tradeLogGateway.getOptionTransactionsBySymbol(accountId, symbol);
        CompletableFuture<List<DividendJournalModel>> futureDividendList = tradeLogGateway.getDividendTransactionsBySymbol(accountId, symbol);

        CompletableFuture.allOf(futureShareList, futureOptionList, futureDividendList).join();

        TradeLogModel tradeLogModel = new TradeLogModel();
        tradeLogModel.setShareList(futureShareList.get());
        tradeLogModel.setOptionList(futureOptionList.get());
        tradeLogModel.setDividendList(futureDividendList.get());
        return tradeLogModel;
    }

    public List<String> getAllTradedSymbols(String accountId) {
        return tradeLogGateway.getAllTradedSymbols(accountId);
    }

    public ShareJournalGWModel createShareTrade(String accountId, ShareJournalGWModel model) {
        ShareJournalModel returnModel = tradeLogGateway.createShareTrade(accountId, ShareJournalConverter.toShareModel.apply(model));
        return ShareJournalConverter.toShareGWModel.apply(returnModel);
    }

    public OptionJournalGWModel createOptionTrade(String accountId, OptionJournalGWModel model) {
        OptionJournalModel returnModel = tradeLogGateway.createOptionTrade(accountId, OptionJournalConverter.toOptionModel.apply(model));
        return OptionJournalConverter.toOptionGWModel.apply(returnModel);
    }

    public void deleteShareTrade(String accountId, String transactionId, String symbol) {
        tradeLogGateway.deleteShareTrade(accountId, transactionId, symbol);
    }

    public void deleteOptionTrade(String accountId, String transactionId, String symbol) {
        tradeLogGateway.deleteOptionTrade(accountId, transactionId, symbol);
    }

    public List<TradeSummaryGWModel> getSummary(String accountId) {
        List<TradeSummaryModel> returnModels = tradeLogGateway.getSummary(accountId);
        return returnModels.stream().map(m -> TradeSummaryConverter.toTradeSummaryGWModel.apply(m)).collect(Collectors.toList());
    }

    public ShareDataGWModel getData(String accountId, String symbol) {
        ShareDataModel returnModel = tradeLogGateway.getShareDataBySymbol(accountId, symbol);
        return ShareDataConverter.toShareDataGWModel.apply(returnModel);
    }

    public void updateGroupOption(String accountId, String transactionId, boolean enabled) {
        TransactionSettingsModel settingsModel = new TransactionSettingsModel();
        settingsModel.setGroupSelected(enabled);
        tradeLogGateway.updateTransactionSettings(accountId, transactionId, settingsModel);
    }

    public void updateGroupSettings(String accountId, List<TransactionSettingsGWModel> modelList) {
        List<TransactionSettingsModel> models = modelList.stream().map(TransactionSettingConverter.toTransactionSettinsGWModel).collect(Collectors.toList());
        tradeLogGateway.updateTransactionSettingsBulk(accountId, models);
    }
}
