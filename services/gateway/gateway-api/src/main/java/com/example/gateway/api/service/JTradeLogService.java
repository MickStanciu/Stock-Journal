//package com.example.gateway.api.service;
//
//import com.example.gateway.api.converter.*;
//import com.example.gateway.api.gateway.JTradeLogGateway;
//import com.example.tradelog.api.spec.model.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//import java.util.stream.Collectors;
//
//@Service
//public class JTradeLogService {
//
//    private static final Logger log = LoggerFactory.getLogger(JTradeLogService.class);
//
//    private JTradeLogGateway tradeLogGateway;
//
//    public JTradeLogService(JTradeLogGateway tradeLogGateway) {
//        this.tradeLogGateway = tradeLogGateway;
//    }
//
//    public TradeLogGWModel getAllBySymbol(String accountId, String symbol) throws ExecutionException, InterruptedException {
//        CompletableFuture<ShareTransactionsResponse> futureShareList = tradeLogGateway.getShareTransactionsBySymbol(accountId, symbol);
//        CompletableFuture<OptionTransactionsResponse> futureOptionList = tradeLogGateway.getOptionTransactionsBySymbol(accountId, symbol);
//        CompletableFuture<DividendTransactionsResponse> futureDividendList = tradeLogGateway.getDividendTransactionsBySymbol(accountId, symbol);
//
//        CompletableFuture.allOf(futureShareList, futureOptionList, futureDividendList).join();
//
//        TradeLogModel tradeLogModel = TradeLogModel.newBuilder().build();
////                .setShareList()
////                .setDividendList()
////                .setOptionList()
////                .build();
////                futureShareList.get().getShareItems(),
////                futureOptionList.get().getOptionItems(),
////                futureDividendList.get().getDividendItems()
//        return new TradeLogModelConverter().apply(tradeLogModel);
//    }
//
//    public ShareJournalGWModel createShareTrade(String accountId, ShareJournalGWModel model) {
//        ShareJournalModel returnModel = tradeLogGateway.createShareTrade(accountId, ShareJournalConverter.toShareModel.apply(model));
//        return ShareJournalConverter.toShareGWModel.apply(returnModel);
//    }
//
//    public OptionJournalGWModel createOptionTrade(String accountId, OptionJournalGWModel model) {
//        OptionJournalModel returnModel = tradeLogGateway.createOptionTrade(accountId, OptionJournalConverter.toOptionModel.apply(model));
//        return OptionJournalConverter.toOptionGWModel.apply(returnModel);
//    }
//
//    public DividendGWModel createDividendRecord(String accountId, DividendGWModel model) {
//        DividendJournalModel returnModel = tradeLogGateway.createDividendTrade(accountId, DividendJournalConverter.toDividendModel.apply(model));
//        return DividendJournalConverter.toDividendGWModel.apply(returnModel);
//    }
//
//    public void updateShareTrade(String accountId, String transactionId, ShareJournalGWModel model) {
//        tradeLogGateway.updateShareTrade(accountId, transactionId, ShareJournalConverter.toShareModel.apply(model));
//    }
//
//    public void deleteShareTrade(String accountId, String transactionId) {
//        tradeLogGateway.deleteShareTrade(accountId, transactionId);
//    }
//
//    public void deleteOptionTrade(String accountId, String transactionId) {
//        tradeLogGateway.deleteOptionTrade(accountId, transactionId);
//    }
//
//    public void deleteDividendRecord(String accountId, String transactionId) {
//        tradeLogGateway.deleteDividendTrade(accountId, transactionId);
//    }
//
//    public List<TradeSummaryGWModel> getSummary(String accountId) {
//        List<TradeSummaryItem> returnModels = tradeLogGateway.getSummary(accountId);
//        return returnModels.stream().map(m -> TradeSummaryConverter.toTradeSummaryGWModel.apply(m)).collect(Collectors.toList());
//    }
//
//    public void updateGroupSettings(String accountId, List<TransactionSettingsGWModel> modelList) {
//        List<TransactionSettingsModel> models = modelList.stream().map(TransactionSettingConverter.toTransactionSettingsGWModel).collect(Collectors.toList());
//        tradeLogGateway.updateTransactionSettingsBulk(accountId, models);
//    }
//
//    public void updateGroupSetting(String accountId, String transactionId, TransactionSettingsGWModel gwModel) {
//        TransactionSettingsModel model = TransactionSettingConverter.toTransactionSettingsGWModel.apply(gwModel);
//        tradeLogGateway.updateTransactionSettings(accountId, transactionId, model);
//    }
//
//}
