package com.example.tradelog.api.service;

import com.example.tradelog.api.repository.SharesJournalRepository;
import com.example.tradelog.api.repository.TransactionRepository;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ShareJournalService {

    private static final Logger log = LoggerFactory.getLogger(ShareJournalService.class);

    private TransactionRepository transactionRepository;
    private SharesJournalRepository sharesJournalRepository;

    public ShareJournalService(TransactionRepository transactionRepository,
                               SharesJournalRepository sharesJournalRepository) {
        this.transactionRepository = transactionRepository;
        this.sharesJournalRepository = sharesJournalRepository;
    }


    public List<ShareJournalModel> getAllBySymbol(String accountId, String symbol) {
        return sharesJournalRepository.getAllBySymbol(accountId, symbol);
    }


    public Optional<ShareJournalModel> createShareRecord(ShareJournalModel model) {
        Optional<String> optionalId = transactionRepository.createTransactionRecord(model.getTransactionModel());

        if (optionalId.isPresent()) {
            sharesJournalRepository.createShareRecord(model);
            return sharesJournalRepository.getByTransactionId(optionalId.get());
        } else {
            return Optional.empty();
        }
    }
}
