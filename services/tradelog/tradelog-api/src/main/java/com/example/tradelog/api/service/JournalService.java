package com.example.tradelog.api.service;

import com.example.tradelog.api.repository.OptionsJournalRepository;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

    private static final Logger log = LoggerFactory.getLogger(JournalService.class);

    private OptionsJournalRepository optionsJournalRepository;

    public JournalService(OptionsJournalRepository optionsJournalRepository) {
        this.optionsJournalRepository = optionsJournalRepository;
    }

    public List<OptionJournalModel> getAllByAccountId(String accountId) {
        return optionsJournalRepository.getAllByAccount(accountId);
    }

    public List<OptionJournalModel> getAllBySymbolAndAccount(String accountId, String symbol) {
        return optionsJournalRepository.getAllBySymbolAndAccount(accountId, symbol);
    }

    public List<String> getAllSymbolsByAccount(String accountId) {
        //TODO: aggregate with STOCKS
        return optionsJournalRepository.getUniqueSymbolsByAccount(accountId);
    }

    public Optional<OptionJournalModel> createOptionRecord(OptionJournalModel model) {
        Optional<String> optionTransactionId = optionsJournalRepository.createOptionRecord(model);
        if (optionTransactionId.isPresent()) {
            return optionsJournalRepository.getByTransactionId(optionTransactionId.get());
        } else {
            return Optional.empty();
        }
    }
}
