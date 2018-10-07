package com.example.stockdata.api.impl.service;

import com.example.stockdata.api.impl.repository.StatsRepository;
import com.example.stockdata.api.spec.model.PriceStatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private StatsRepository statsRepository;

    @Autowired
    public StatsService(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public PriceStatModel getPriceStats(String symbol) {
        return statsRepository.getPriceStats(symbol);
    }

    public void updateStats(PriceStatModel model) {
        statsRepository.updateStandardDeviation(model);
    }

    public void insertStats(PriceStatModel model) {
        statsRepository.createPriceStats(model);
    }
}
