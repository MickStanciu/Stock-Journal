package com.example.tradelog.api.service;

import com.example.tradelog.api.converter.SyntheticSharesGenerator;
import com.example.tradelog.api.converter.TradeSummaryListConverter;
import com.example.tradelog.api.core.model.ShareJournalModel;
import com.example.tradelog.api.core.model.TradeSummaryModel;
import com.example.tradelog.api.repository.JSharesJournalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class JShareJournalService {

    private JSharesJournalRepository sharesJournalRepository;

    public JShareJournalService(JSharesJournalRepository sharesJournalRepository) {
        this.sharesJournalRepository = sharesJournalRepository;
    }


    /**
     * Returns all shares per symbol including a calculated synthetic one
     * @param accountId -
     * @param symbol -
     * @return list
     */
    public List<ShareJournalModel> getAllBySymbol(String accountId, String symbol) {
        List<ShareJournalModel> shareJournalModelList = sharesJournalRepository.getAllBySymbol(accountId, symbol);
        shareJournalModelList.addAll(SyntheticSharesGenerator.createSynthetic.apply(shareJournalModelList));
        return shareJournalModelList;
    }


    public Optional<ShareJournalModel> createRecord(String transactionId, ShareJournalModel model) {
        sharesJournalRepository.createShareRecord(transactionId, model);
        return sharesJournalRepository.getByTransactionId(transactionId);
    }

    public boolean updateShareRecord(ShareJournalModel model) {
        return sharesJournalRepository.updateShareRecord(model);
    }

    public boolean deleteRecord(String transactionId) {
        return sharesJournalRepository.deleteRecord(transactionId);
    }

    public Map<String, TradeSummaryModel> getSummaries(String accountId) {
        List<TradeSummaryModel> modelList = sharesJournalRepository.getSummaries(accountId);
        return TradeSummaryListConverter.toMap.apply(modelList);
    }
}
