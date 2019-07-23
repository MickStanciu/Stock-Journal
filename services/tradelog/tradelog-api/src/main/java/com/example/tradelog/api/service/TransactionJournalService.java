package com.example.tradelog.api.service;

import com.example.tradelog.api.repository.TransactionRepository;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;
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

    public boolean updateOptions(TransactionSettingsModel model) {
        return transactionRepository.updateSettings(model);
    }

    public void updateSettingsBulk(List<TransactionSettingsModel> models) {
        //todo: find a better way
        for (TransactionSettingsModel model : models) {
            if (!transactionRepository.updateSettings(model)) {
                log.error("Failed to update settings for {}", model.getTransactionId());
            }
        }
    }
}
