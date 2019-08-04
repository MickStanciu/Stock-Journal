package com.example.tradelog.api.service;

import com.example.tradelog.api.converter.TradeSummaryListConverter;
import com.example.tradelog.api.repository.DividendRepository;
import com.example.tradelog.api.spec.model.DividendJournalModel;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DividendJournalService {

    private static final Logger log = LoggerFactory.getLogger(DividendJournalService.class);

    private DividendRepository dividendRepository;

    public DividendJournalService(DividendRepository dividendRepository) {
        this.dividendRepository = dividendRepository;
    }

    public List<DividendJournalModel> getAllBySymbol(String accountId, String symbol) {
        return dividendRepository.getAllBySymbol(accountId, symbol);
    }

    public Map<String, TradeSummaryModel> getSummaries(String accountId) {
        List<TradeSummaryModel> modelList = dividendRepository.getSummaries(accountId);
        return TradeSummaryListConverter.toMap.apply(modelList);
    }

    public Optional<DividendJournalModel> createDividendRecord(String transactionId, DividendJournalModel model) {
        dividendRepository.createRecord(transactionId, model);
        return dividendRepository.getByTransactionId(transactionId);
    }
}
