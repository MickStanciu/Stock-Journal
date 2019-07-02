package com.example.tradelog.api.service;

import com.example.tradelog.api.repository.SharesJournalRepository;
import com.example.tradelog.api.repository.TransactionRepository;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class ShareJournalService {

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


    //TODO: if the second leg fails, delete the first one. Transactional
    public Optional<ShareJournalModel> createShareRecord(ShareJournalModel model) {
        Optional<String> optionalId = transactionRepository.createTransactionRecord(model.getTransactionDetails());

        if (optionalId.isPresent()) {
            sharesJournalRepository.createShareRecord(optionalId.get(), model);
            return sharesJournalRepository.getByTransactionId(optionalId.get());
        } else {
            return Optional.empty();
        }
    }


    public boolean deleteShareRecord(String accountId, String transactionId, String symbol) {
        return sharesJournalRepository.deleteRecord(transactionId)
                && transactionRepository.deleteShareRecord(transactionId, accountId, symbol);
    }


    public Map<String, TradeSummaryModel> getSummaries(String accountId) {
        Map<String, TradeSummaryModel> summaryModelMap = new HashMap<>();

        List<TradeSummaryModel> modelList = sharesJournalRepository.getSummaries(accountId);

        modelList.forEach(m -> {
            if (summaryModelMap.containsKey(m.getSymbol())) {
                TradeSummaryModel storedModel = summaryModelMap.get(m.getSymbol());
                summaryModelMap.put(m.getSymbol(), TradeSummaryModel.builder()
                        .withSymbol(storedModel.getSymbol())
                        .withTrades(storedModel.getTrades() + 1)
                        .withTotal(storedModel.getTotal() + m.getTotal())
                        .build());
            } else {
                summaryModelMap.put(m.getSymbol(), m);
            }
        });

        return summaryModelMap;
    }
}
