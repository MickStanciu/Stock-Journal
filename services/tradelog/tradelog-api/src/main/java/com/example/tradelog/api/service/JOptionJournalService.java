package com.example.tradelog.api.service;

import com.example.tradelog.api.core.model.OptionJournalModel;
import com.example.tradelog.api.repository.JOptionsJournalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JOptionJournalService {

    private JOptionsJournalRepository optionsJournalRepository;

    public JOptionJournalService(JOptionsJournalRepository optionsJournalRepository) {
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
}
