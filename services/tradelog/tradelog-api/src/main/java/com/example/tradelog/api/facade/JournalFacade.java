package com.example.tradelog.api.facade;

import com.example.tradelog.api.service.DividendJournalService;
import com.example.tradelog.api.service.OptionJournalService;
import com.example.tradelog.api.service.ShareJournalService;
import com.example.tradelog.api.service.TransactionJournalService;
import com.example.tradelog.api.spec.model.DividendJournalModel;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
                summaryModelMap.put(v.getSymbol(), new TradeSummaryModel(storedModel.getSymbol(), storedModel.getTrades() + v.getTrades(), storedModel.getTotal().add(v.getTotal())));
            } else {
                summaryModelMap.put(v.getSymbol(), v);
            }
        });

        dividendSummaries.forEach( (k, v) -> {
            if (summaryModelMap.containsKey(v.getSymbol())) {
                TradeSummaryModel storedModel = summaryModelMap.get(v.getSymbol());
                summaryModelMap.put(v.getSymbol(), new TradeSummaryModel(storedModel.getSymbol(), storedModel.getTrades() + v.getTrades(), storedModel.getTotal().add(v.getTotal())));
            } else {
                summaryModelMap.put(v.getSymbol(), v);
            }
        });


        return summaryModelMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public boolean updateOptions(TransactionSettingsModel model) {
        return transactionService.updateOptions(model);
    }

    public void updateSettingsBulk(List<TransactionSettingsModel> models) {
        transactionService.updateSettingsBulk(models);
    }

    public List<DividendJournalModel> getAllDividendsBySymbol(String accountId, String symbol) {
        return dividendService.getAllBySymbol(accountId, symbol);
    }

    public Optional<DividendJournalModel> createDividendRecord(DividendJournalModel model) {
        Optional<String> optionalId = transactionService.createTransactionRecord(model.getTransactionDetails());
        if (optionalId.isPresent()) {
            return dividendService.createRecord(optionalId.get(), model);
        }
        return Optional.empty();
    }

    //TODO: transactional
    public boolean deleteDividendRecord(String accountId, String transactionId) {
        return dividendService.deleteRecord(transactionId)
                && transactionService.deleteSettingsRecord(transactionId)
                && transactionService.deleteDividendRecord(transactionId, accountId);
    }


    public List<ShareJournalModel> getAllSharesBySymbol(String accountId, String symbol) {
        return shareService.getAllBySymbol(accountId, symbol);
    }

    public Optional<ShareJournalModel> createShareRecord(ShareJournalModel model) {
        Optional<String> optionalId = transactionService.createTransactionRecord(model.getTransactionDetails());
        if (optionalId.isPresent()) {
            return shareService.createRecord(optionalId.get(), model);
        }
        return Optional.empty();
    }

    //TODO: should be transactional
    public boolean deleteShareRecord(String accountId, String transactionId) {
        return shareService.deleteRecord(transactionId)
                && transactionService.deleteSettingsRecord(transactionId)
                && transactionService.deleteShareRecord(transactionId, accountId);
    }

    public List<OptionJournalModel> getAllOptionsBySymbol(String accountId, String symbol) {
        return optionService.getAllBySymbol(accountId, symbol);
    }

    public Optional<OptionJournalModel> createOptionRecord(OptionJournalModel model) {
        Optional<String> optionalId = transactionService.createTransactionRecord(model.getTransactionDetails());
        if (optionalId.isPresent()) {
            return optionService.createRecord(optionalId.get(), model);
        }
        return Optional.empty();
    }

    public boolean deleteOptionRecord(String accountId, String transactionId) {
        return optionService.deleteRecord(transactionId)
                && transactionService.deleteSettingsRecord(transactionId)
                && transactionService.deleteOptionRecord(transactionId, accountId);
    }
}