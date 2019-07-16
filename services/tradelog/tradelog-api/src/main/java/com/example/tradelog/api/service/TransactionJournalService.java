package com.example.tradelog.api.service;

import com.example.tradelog.api.repository.TransactionRepository;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import com.example.tradelog.api.spec.model.TransactionOptionsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionJournalService {

    private static final Logger log = LoggerFactory.getLogger(TransactionJournalService.class);

    private TransactionRepository transactionRepository;

    public TransactionJournalService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<String> getAllTradedSymbols(String accountId) {
        return transactionRepository.getUniqueSymbols(accountId);
    }

    public boolean updateOptions(String accountId, String transactionId, TransactionOptionsModel model) {
        return transactionRepository.updateOptions(accountId, transactionId, model);
    }
}
