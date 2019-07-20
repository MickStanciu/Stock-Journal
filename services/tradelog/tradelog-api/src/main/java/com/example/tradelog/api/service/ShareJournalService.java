package com.example.tradelog.api.service;

import com.example.tradelog.api.converter.SyntheticSharesGenerator;
import com.example.tradelog.api.converter.TradeSummaryListConverter;
import com.example.tradelog.api.repository.SharesJournalRepository;
import com.example.tradelog.api.repository.TransactionRepository;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import com.example.tradelog.api.spec.model.TransactionOptionsModel;
import org.springframework.stereotype.Service;

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


    //TODO: if the second leg fails, delete the first one. Transactional
    public Optional<ShareJournalModel> createShareRecord(ShareJournalModel model) {
        Optional<String> optionalId = transactionRepository.createTransactionRecord(model.getTransactionDetails());

        boolean transactionSettingsSuccess = false;
        if (optionalId.isPresent()) {
            TransactionOptionsModel transactionOptionsModel = new TransactionOptionsModel();
            transactionOptionsModel.setGroupSelected(true);
            transactionOptionsModel.setLegClosed(false);
            transactionSettingsSuccess = transactionRepository.createSettings(optionalId.get(), transactionOptionsModel);
        }

        if (optionalId.isPresent() && transactionSettingsSuccess) {
            sharesJournalRepository.createShareRecord(optionalId.get(), model);
            return sharesJournalRepository.getByTransactionId(optionalId.get());
        } else {
            return Optional.empty();
        }
    }


    public boolean deleteShareRecord(String accountId, String transactionId, String symbol) {
        return sharesJournalRepository.deleteRecord(transactionId)
                && transactionRepository.deleteSettingsRecord(transactionId)
                && transactionRepository.deleteShareRecord(transactionId, accountId, symbol);
    }


    public Map<String, TradeSummaryModel> getSummaries(String accountId) {
        List<TradeSummaryModel> modelList = sharesJournalRepository.getSummaries(accountId);
        return TradeSummaryListConverter.toMap.apply(modelList);
    }
}
