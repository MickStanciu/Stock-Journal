package com.example.stockdata.api.impl.strategy;

import com.example.stockdata.api.impl.repository.HistoryRepository;
import com.example.stockdata.api.impl.repository.StatsRepository;
import com.example.stockdata.api.spec.model.PriceModel;
import com.example.stockdata.api.spec.model.PriceStatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CashSecuredPut {

    private static final Logger log = LoggerFactory.getLogger(CashSecuredPut.class);

    private HistoryRepository historyRepository;
    private StatsRepository statsRepository;

    private double moneyValue = 10000;
    private double risk = 100;
    private boolean inTrade = false;

    private long positionSize = 0;
    private double entryPrice = 0;
    private double stopLoss = 0;

    @Autowired
    public CashSecuredPut(HistoryRepository historyRepository, StatsRepository statsRepository) {
        this.historyRepository = historyRepository;
        this.statsRepository = statsRepository;
    }

    public void run() {
        List<PriceModel> priceModelList = historyRepository.getLastYearPricesForSymbol("XYZ");
        log.info("Found: " + priceModelList.size() + " items");

        PriceStatModel priceStatModel = statsRepository.getPriceStats("XYZ");

        log.info("Initial capital: " + this.moneyValue);


        for (PriceModel price : priceModelList) {
            if (!inTrade) {
                enterTrade(price, priceStatModel.getStd());
            }

            //check Stop Loss
            if (price.getAdjClose() <= this.stopLoss) {
                exitTrade(price, this.stopLoss);
            }
        }

        log.info("Ending capital: " + this.moneyValue);
    }

    private void enterTrade(PriceModel price, double std) {
        this.entryPrice = price.getAdjClose() - price.getAdjClose() * std * 3;
//        this.stopLoss = price.getAdjClose() - (6.0/100.0) * this.entryPrice;
        this.stopLoss = this.entryPrice;
        double riskAmount = this.moneyValue * (this.risk / 100);
        this.positionSize = Math.round(riskAmount / price.getAdjClose());
        moneyValue -= this.positionSize * this.entryPrice;

        inTrade = true;
        log.info("Enter trade on " + price.getDate().toString() + " at " + entryPrice + " for XYZ " + this.positionSize + " shares");
        log.info(" ... stop loss: " + this.stopLoss);
    }

    private void exitTrade(PriceModel price, double exitPrice) {
        moneyValue += this.positionSize * exitPrice;

        inTrade = false;
        log.info("Exit trade on " + price.getDate().toString() + " at " + exitPrice + " for XYZ " + this.positionSize + " shares");
    }
}
