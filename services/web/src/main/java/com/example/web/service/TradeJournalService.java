package com.example.web.service;

import com.example.gateway.api.model.TradeLogModelGW;
import com.example.web.converter.SynteticSharesGenerator;
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

//    public List<OptionJournalGWModel> getAllByAccountId(String accountId) {
//        return tradeJournalGateway.getAllByAccountId(accountId);
//    }

    public TradeLogModel getAllTradesBySymbol(String accountId, String symbol) {
        TradeLogModelGW tradeLogModelGW = tradeJournalGateway.getAllTradesBySymbol(accountId, symbol);
        TradeLogModel tradeLogModel = new TradeLogModelConverter().apply(tradeLogModelGW);
        tradeLogModel.setSyntheticShareList(new SynteticSharesGenerator().apply(tradeLogModelGW.getShareList()));
        return tradeLogModel;
    }

    public List<String> getUniqueSymbols(String accountId) {
        return tradeJournalGateway.getUniqueSymbols(accountId);
    }
}
