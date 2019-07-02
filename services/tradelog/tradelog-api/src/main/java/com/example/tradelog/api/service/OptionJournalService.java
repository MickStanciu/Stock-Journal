package com.example.tradelog.api.service;

import com.example.tradelog.api.repository.OptionsJournalRepository;
import com.example.tradelog.api.repository.TransactionRepository;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OptionJournalService {

    private TransactionRepository transactionRepository;
    private OptionsJournalRepository optionsJournalRepository;

    public OptionJournalService(TransactionRepository transactionRepository, OptionsJournalRepository optionsJournalRepository) {
        this.transactionRepository = transactionRepository;
        this.optionsJournalRepository = optionsJournalRepository;
    }


    public List<OptionJournalModel> getAllBySymbol(String accountId, String symbol) {
        return optionsJournalRepository.getAllBySymbol(accountId, symbol);
    }


    //TODO: if the second leg fails, delete the first one. Transactional
    public Optional<OptionJournalModel> createOptionRecord(OptionJournalModel model) {
        Optional<String> optionalId = transactionRepository.createTransactionRecord(model.getTransactionDetails());

        if (optionalId.isPresent()) {
            optionsJournalRepository.createOptionRecord(optionalId.get(), model);
            return optionsJournalRepository.getByTransactionId(optionalId.get());
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteOptionRecord(String accountId, String transactionId, String symbol) {
        return optionsJournalRepository.deleteRecord(transactionId)
                && transactionRepository.deleteOptionRecord(transactionId, accountId, symbol);
    }
}
