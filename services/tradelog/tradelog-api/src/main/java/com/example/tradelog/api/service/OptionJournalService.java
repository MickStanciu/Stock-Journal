package com.example.tradelog.api.service;

import com.example.tradelog.api.repository.OptionsJournalRepository;
import com.example.tradelog.api.repository.TransactionRepository;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OptionJournalService {

    private static final Logger log = LoggerFactory.getLogger(OptionJournalService.class);

    private TransactionRepository transactionRepository;
    private OptionsJournalRepository optionsJournalRepository;

    public OptionJournalService(TransactionRepository transactionRepository, OptionsJournalRepository optionsJournalRepository) {
        this.transactionRepository = transactionRepository;
        this.optionsJournalRepository = optionsJournalRepository;
    }

    public List<OptionJournalModel> getAllBySymbol(String accountId, String symbol) {
        return optionsJournalRepository.getAllBySymbol(accountId, symbol);
    }

    public Optional<OptionJournalModel> createOptionRecord(OptionJournalModel model) {
        Optional<String> optionalId = transactionRepository.createTransactionRecord(model.getTransactionModel());

        if (optionalId.isPresent()) {
            optionsJournalRepository.createOptionRecord(model);
            return optionsJournalRepository.getByTransactionId(optionalId.get());
        } else {
            return Optional.empty();
        }
    }
}
