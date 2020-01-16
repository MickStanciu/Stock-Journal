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
//    public DividendGWModel createDividendRecord(String accountId, DividendGWModel model) {
//        DividendJournalModel returnModel = tradeLogGateway.createDividendTrade(accountId, DividendJournalConverter.toDividendModel.apply(model));
//        return DividendJournalConverter.toDividendGWModel.apply(returnModel);
//    }
//
//    public void updateShareTrade(String accountId, String transactionId, ShareJournalGWModel model) {
//        tradeLogGateway.updateShareTrade(accountId, transactionId, ShareJournalConverter.toShareModel.apply(model));
//    }
//
//}
