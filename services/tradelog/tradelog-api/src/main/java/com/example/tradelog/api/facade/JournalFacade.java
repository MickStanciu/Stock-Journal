package com.example.tradelog.api.facade;

import com.example.tradelog.api.service.DividendJournalService;
import com.example.tradelog.api.service.OptionJournalService;
import com.example.tradelog.api.service.ShareJournalService;
import com.example.tradelog.api.service.TransactionJournalService;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<TradeSummaryModel> shareSummaries = this.shareService.getSummaries(accountId);


        List<TradeSummaryModel> models = new ArrayList<>();

        return models;
    }
}
