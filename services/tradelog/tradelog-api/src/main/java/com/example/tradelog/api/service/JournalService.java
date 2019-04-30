package com.example.tradelog.api.service;

import com.example.tradelog.api.repository.OptionsJournalRepository;
import com.example.tradelog.api.repository.SharesJournalRepository;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.TradeLogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JournalService {

    private static final Logger log = LoggerFactory.getLogger(JournalService.class);

    private OptionsJournalRepository optionsJournalRepository;
    private SharesJournalRepository sharesJournalRepository;

    public JournalService(OptionsJournalRepository optionsJournalRepository, SharesJournalRepository sharesJournalRepository) {
        this.optionsJournalRepository = optionsJournalRepository;
        this.sharesJournalRepository = sharesJournalRepository;
    }

//    public List<OptionJournalModel> getAllByAccountId(String accountId) {
//        return optionsJournalRepository.getAllByAccount(accountId);
//    }

    public TradeLogModel getAllBySymbol(String accountId, String symbol) {
        TradeLogModel tradeLogModel = new TradeLogModel();
        tradeLogModel.setOptionList(optionsJournalRepository.getAllBySymbol(accountId, symbol));
        tradeLogModel.setShareList(sharesJournalRepository.getAllBySymbol(accountId, symbol));
        return tradeLogModel;
    }

    public Set<String> getAllTradedSymbols(String accountId) {
        List<String> shares = sharesJournalRepository.getUniqueSymbols(accountId);
        List<String> options = optionsJournalRepository.getUniqueSymbols(accountId);

        Set<String> combined = new TreeSet<>();
        combined.addAll(shares);
        combined.addAll(options);
        return combined;
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
