package com.example.tradelog.api.service;

import com.example.tradelog.api.repository.OptionsJournalRepository;
import com.example.tradelog.api.repository.SharesJournalRepository;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class JournalService {

    private static final Logger log = LoggerFactory.getLogger(JournalService.class);

    private OptionsJournalRepository optionsJournalRepository;
    private SharesJournalRepository sharesJournalRepository;

    public JournalService(OptionsJournalRepository optionsJournalRepository, SharesJournalRepository sharesJournalRepository) {
        this.optionsJournalRepository = optionsJournalRepository;
        this.sharesJournalRepository = sharesJournalRepository;
    }

    public List<OptionJournalModel> getAllByAccountId(String accountId) {
        return optionsJournalRepository.getAllByAccount(accountId);
    }

    public List<OptionJournalModel> getAllBySymbolAndAccount(String accountId, String symbol) {
        return optionsJournalRepository.getAllBySymbolAndAccount(accountId, symbol);
    }

    public Set<String> getAllTradedSymbols(String accountId) {
        List<String> shares = sharesJournalRepository.getUniqueSymbols(accountId);
        List<String> options = optionsJournalRepository.getUniqueSymbols(accountId);

        Set<String> set = new HashSet<>();
        set.addAll(shares);
        set.addAll(options);
        return set;
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
