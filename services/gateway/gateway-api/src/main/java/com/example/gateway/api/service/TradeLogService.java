package com.example.gateway.api.service;

import com.example.gateway.api.converter.DividendJournalConverter;
import com.example.gateway.api.converter.OptionJournalConverter;
import com.example.gateway.api.converter.ShareDataConverter;
import com.example.gateway.api.converter.ShareJournalConverter;
import com.example.gateway.api.converter.TradeLogModelConverter;
import com.example.gateway.api.converter.TradeSummaryConverter;
import com.example.gateway.api.converter.TransactionSettingConverter;
import com.example.gateway.api.gateway.TradeLogGateway;
import com.example.gateway.api.model.DividendGWModel;
import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.gateway.api.model.ShareDataGWModel;
import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.gateway.api.model.TradeLogGWModel;
import com.example.gateway.api.model.TradeSummaryGWModel;
import com.example.gateway.api.model.TransactionSettingsGWModel;
import com.example.tradelog.api.spec.model.DividendJournalModel;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.ShareDataModel;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import com.example.tradelog.api.spec.model.TradeLogModel;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;
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

    public TradeLogGWModel getAllBySymbol(String accountId, String symbol) throws ExecutionException, InterruptedException {
        CompletableFuture<List<ShareJournalModel>> futureShareList = tradeLogGateway.getShareTransactionsBySymbol(accountId, symbol);
        CompletableFuture<List<OptionJournalModel>> futureOptionList = tradeLogGateway.getOptionTransactionsBySymbol(accountId, symbol);
        CompletableFuture<List<DividendJournalModel>> futureDividendList = tradeLogGateway.getDividendTransactionsBySymbol(accountId, symbol);

        CompletableFuture.allOf(futureShareList, futureOptionList, futureDividendList).join();

        TradeLogModel tradeLogModel = new TradeLogModel();
        tradeLogModel.setShareList(futureShareList.get());
        tradeLogModel.setOptionList(futureOptionList.get());
        tradeLogModel.setDividendList(futureDividendList.get());

        return new TradeLogModelConverter().apply(tradeLogModel);
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

    public DividendGWModel createDividendRecord(String accountId, DividendGWModel model) {
        DividendJournalModel returnModel = tradeLogGateway.createDividendTrade(accountId, DividendJournalConverter.toDividendModel.apply(model));
        return DividendJournalConverter.toDividendGWModel.apply(returnModel);
    }

    public void deleteShareTrade(String accountId, String transactionId, String symbol) {
        tradeLogGateway.deleteShareTrade(accountId, transactionId, symbol);
    }

    public void deleteOptionTrade(String accountId, String transactionId, String symbol) {
        tradeLogGateway.deleteOptionTrade(accountId, transactionId, symbol);
    }

    public void deleteDividendRecord(String accountId, String transactionId, String symbol) {
        tradeLogGateway.deleteDividendTrade(accountId, transactionId, symbol);
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
        TransactionSettingsModel settingsModel = TransactionSettingsModel.builder()
                .withGroupSelected(enabled)
                .build();
        tradeLogGateway.updateTransactionSettings(accountId, transactionId, settingsModel);
    }

    public void updateGroupSettings(String accountId, List<TransactionSettingsGWModel> modelList) {
        List<TransactionSettingsModel> models = modelList.stream().map(TransactionSettingConverter.toTransactionSettingsGWModel).collect(Collectors.toList());
        tradeLogGateway.updateTransactionSettingsBulk(accountId, models);
    }

    public void updateGroupSetting(String accountId, String transactionId, TransactionSettingsGWModel gwModel) {
        TransactionSettingsModel model = TransactionSettingConverter.toTransactionSettingsGWModel.apply(gwModel);
        tradeLogGateway.updateTransactionSettings(accountId, transactionId, model);
    }
}
