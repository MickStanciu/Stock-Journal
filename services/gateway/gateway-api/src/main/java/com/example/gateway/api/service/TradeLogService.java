package com.example.gateway.api.service;

import com.example.gateway.api.gateway.TradeLogGateway;
import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import com.example.tradelog.api.spec.model.TradeLogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TradeLogService {

    private static final Logger log = LoggerFactory.getLogger(TradeLogService.class);

    private TradeLogGateway tradeLogGateway;

    public TradeLogService(TradeLogGateway tradeLogGateway) {
        this.tradeLogGateway = tradeLogGateway;
    }

    public TradeLogModel getAllBySymbol(String accountId, String symbol) {
        List<ShareJournalModel> shareList = tradeLogGateway.getShareTransactionsBySymbol(accountId, symbol);

        TradeLogModel tradeLogModel = new TradeLogModel();
        tradeLogModel.setShareList(shareList);
        tradeLogModel.setDividendList(Collections.emptyList());
        tradeLogModel.setOptionList(Collections.emptyList());
        return tradeLogModel;
    }

    public List<String> getAllTradedSymbols(String accountId) {
        return tradeLogGateway.getAllTradedSymbols(accountId);
    }

    public ShareJournalGWModel createShareTrade(String accountId, ShareJournalGWModel model) {
        return tradeLogGateway.createShareTrade(accountId, model);
    }

    public OptionJournalGWModel createOptionTrade(String accountId, OptionJournalGWModel model) {
        return tradeLogGateway.createOptionTrade(accountId, model);
    }
}
