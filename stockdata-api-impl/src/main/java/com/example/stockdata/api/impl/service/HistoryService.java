package com.example.stockdata.api.impl.service;

import com.example.stockdata.api.impl.repository.HistoryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class HistoryService {

    private HistoryRepository historyRepository;

    @Inject
    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public List<Double> getHistoryClose(String symbol) {
        return historyRepository.getAdjustedCloseValues(symbol);
    }
}
