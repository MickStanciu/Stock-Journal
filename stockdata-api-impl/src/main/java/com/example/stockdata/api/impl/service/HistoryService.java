package com.example.stockdata.api.impl.service;

import com.example.stockdata.api.impl.repository.HistoryRepository;
import com.example.stockdata.api.spec.model.PriceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    private HistoryRepository historyRepository;

    @Autowired
    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public List<PriceModel> getPricesForSymbol(String symbol, int batchSize) {
        return historyRepository.getPricesForSymbol(symbol, batchSize);
    }

    public void updatePrices(List<PriceModel> priceModelList) {
        historyRepository.updatePrices(priceModelList);
    }
}
