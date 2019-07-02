package com.example.tradelog.api.facade;

import com.example.tradelog.api.service.DividendJournalService;
import com.example.tradelog.api.service.OptionJournalService;
import com.example.tradelog.api.service.ShareJournalService;
import com.example.tradelog.api.service.TransactionJournalService;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class JournalFacade {

    private TransactionJournalService transactionService;
    private ShareJournalService shareService;
    private OptionJournalService optionService;
    private DividendJournalService dividendService;

    public JournalFacade(TransactionJournalService transactionService, ShareJournalService shareService, OptionJournalService optionService, DividendJournalService dividendService) {
        this.transactionService = transactionService;
        this.shareService = shareService;
        this.optionService = optionService;
        this.dividendService = dividendService;
    }


    public List<String> getAllTradedSymbols(String accountId) {
        return transactionService.getAllTradedSymbols(accountId);
    }

    public List<TradeSummaryModel> getSummary(String accountId) {
        Map<String, TradeSummaryModel> shareSummaries = this.shareService.getSummaries(accountId);
        Map<String, TradeSummaryModel> optionSummaries = this.optionService.getSummaries(accountId);
        Map<String, TradeSummaryModel> dividendSummaries = this.dividendService.getSummaries(accountId);

        Map<String, TradeSummaryModel> summaryModelMap = new HashMap<>();
        shareSummaries.forEach(summaryModelMap::put);


        optionSummaries.forEach( (k, v) -> {
            if (summaryModelMap.containsKey(v.getSymbol())) {
                TradeSummaryModel storedModel = summaryModelMap.get(v.getSymbol());
                summaryModelMap.put(v.getSymbol(), TradeSummaryModel.builder()
                        .withSymbol(storedModel.getSymbol())
                        .withTrades(storedModel.getTrades() + v.getTrades())
                        .withTotal(storedModel.getTotal() + v.getTotal())
                        .build());
            } else {
                summaryModelMap.put(v.getSymbol(), v);
            }
        });

        dividendSummaries.forEach( (k, v) -> {
            if (summaryModelMap.containsKey(v.getSymbol())) {
                TradeSummaryModel storedModel = summaryModelMap.get(v.getSymbol());
                summaryModelMap.put(v.getSymbol(), TradeSummaryModel.builder()
                        .withSymbol(storedModel.getSymbol())
                        .withTrades(storedModel.getTrades() + v.getTrades())
                        .withTotal(storedModel.getTotal() + v.getTotal())
                        .build());
            } else {
                summaryModelMap.put(v.getSymbol(), v);
            }
        });


        List<TradeSummaryModel> models = new ArrayList<>();
        summaryModelMap.forEach( (key, val) -> models.add(val));
        return models;
    }
}
