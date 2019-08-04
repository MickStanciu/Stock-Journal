package com.example.tradelog.api.service;

import com.example.tradelog.api.converter.TradeSummaryListConverter;
import com.example.tradelog.api.repository.OptionsJournalRepository;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OptionJournalService {

    private OptionsJournalRepository optionsJournalRepository;

    public OptionJournalService(OptionsJournalRepository optionsJournalRepository) {
        this.optionsJournalRepository = optionsJournalRepository;
    }


    public List<OptionJournalModel> getAllBySymbol(String accountId, String symbol) {
        return optionsJournalRepository.getAllBySymbol(accountId, symbol);
    }

    public Optional<OptionJournalModel> createRecord(String transactionId, OptionJournalModel model) {
        optionsJournalRepository.createOptionRecord(transactionId, model);
        return optionsJournalRepository.getByTransactionId(transactionId);
    }

    public boolean deleteRecord(String transactionId) {
        return optionsJournalRepository.deleteRecord(transactionId);
    }

    public Map<String, TradeSummaryModel> getSummaries(String accountId) {
        List<TradeSummaryModel> modelList = optionsJournalRepository.getSummaries(accountId);
        return TradeSummaryListConverter.toMap.apply(modelList);
    }
}
