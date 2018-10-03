package com.example.stockdata.api.impl.service;

import com.example.stockdata.api.impl.repository.HistoryRepository;
import com.example.stockdata.api.spec.model.PriceModel;
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

    public List<PriceModel> getPricesForSymbol(String symbol) {
        return historyRepository.getPricesForSymbol(symbol);
    }

    public void updatePrices(List<PriceModel> priceModelList) {
        historyRepository.updatePrices(priceModelList);
    }
}
