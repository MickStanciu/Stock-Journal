package com.example.tradelog.api.service;

import com.example.tradelog.api.converter.TradeSummaryListConverter;
import com.example.tradelog.api.repository.DividendRepository;
import com.example.tradelog.api.repository.TransactionRepository;
import com.example.tradelog.api.spec.model.DividendJournalModel;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DividendJournalService {

    private static final Logger log = LoggerFactory.getLogger(DividendJournalService.class);

    private TransactionRepository transactionRepository;
    private DividendRepository dividendRepository;

    public DividendJournalService(TransactionRepository transactionRepository, DividendRepository dividendRepository) {
        this.transactionRepository = transactionRepository;
        this.dividendRepository = dividendRepository;
    }

    public List<DividendJournalModel> getAllBySymbol(String accountId, String symbol) {
        return dividendRepository.getAllBySymbol(accountId, symbol);
    }

    public Map<String, TradeSummaryModel> getSummaries(String accountId) {
        List<TradeSummaryModel> modelList = dividendRepository.getSummaries(accountId);
        return TradeSummaryListConverter.toMap.apply(modelList);
    }
}
