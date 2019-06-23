package com.example.tradelog.api.service;

import com.example.tradelog.api.repository.DividendRepository;
import com.example.tradelog.api.repository.OptionsJournalRepository;
import com.example.tradelog.api.repository.SharesJournalRepository;
import com.example.tradelog.api.repository.TransactionRepository;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import com.example.tradelog.api.spec.model.TradeLogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JournalService {

    private static final Logger log = LoggerFactory.getLogger(JournalService.class);

    private OptionsJournalRepository optionsJournalRepository;
    private SharesJournalRepository sharesJournalRepository;
    private TransactionRepository transactionRepository;
    private DividendRepository dividendRepository;

    public JournalService(OptionsJournalRepository optionsJournalRepository,
                          SharesJournalRepository sharesJournalRepository,
                          TransactionRepository transactionRepository,
                          DividendRepository dividendRepository) {
        this.optionsJournalRepository = optionsJournalRepository;
        this.sharesJournalRepository = sharesJournalRepository;
        this.transactionRepository = transactionRepository;
        this.dividendRepository = dividendRepository;
    }

    public TradeLogModel getAllBySymbol(String accountId, String symbol) {
        TradeLogModel tradeLogModel = new TradeLogModel();
        tradeLogModel.setOptionList(optionsJournalRepository.getAllBySymbol(accountId, symbol));
        tradeLogModel.setShareList(sharesJournalRepository.getAllBySymbol(accountId, symbol));
        tradeLogModel.setDividendList(dividendRepository.getAllBySymbol(accountId, symbol));
        return tradeLogModel;
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

    public Optional<ShareJournalModel> createShareRecord(ShareJournalModel model) {
        Optional<String> optionalId = transactionRepository.createTransactionRecord(model.getTransactionModel());

        if (optionalId.isPresent()) {
            sharesJournalRepository.createShareRecord(model);
            return sharesJournalRepository.getByTransactionId(optionalId.get());
        } else {
            return Optional.empty();
        }
    }

    public List<String> getAllTradedSymbols(String accountId) {
        return transactionRepository.getUniqueSymbols(accountId);
    }
}
