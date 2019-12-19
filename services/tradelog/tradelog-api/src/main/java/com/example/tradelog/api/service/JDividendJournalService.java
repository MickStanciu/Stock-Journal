package com.example.tradelog.api.service;

import com.example.tradelog.api.converter.TradeSummaryListConverter;
import com.example.tradelog.api.core.model.DividendJournalModel;
import com.example.tradelog.api.core.model.TradeSummaryModel;
import com.example.tradelog.api.repository.DividendRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class JDividendJournalService {

    private DividendRepository dividendRepository;

    public JDividendJournalService(DividendRepository dividendRepository) {
        this.dividendRepository = dividendRepository;
    }

    public List<DividendJournalModel> getAllBySymbol(String accountId, String symbol) {
        return dividendRepository.getAllBySymbol(accountId, symbol);
    }

    public Optional<DividendJournalModel> createRecord(String transactionId, DividendJournalModel model) {
        dividendRepository.createRecord(transactionId, model);
        return dividendRepository.getByTransactionId(transactionId);
    }

    public boolean deleteRecord(String transactionId) {
        return dividendRepository.deleteRecord(transactionId);
    }

    public Map<String, TradeSummaryModel> getSummaries(String accountId) {
        List<TradeSummaryModel> modelList = dividendRepository.getSummaries(accountId);
        return TradeSummaryListConverter.toMap.apply(modelList);
    }
}
