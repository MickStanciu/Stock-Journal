package com.example.stockdata.api.impl.calculators;

import com.example.stockdata.api.spec.model.PriceModel;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class DataProcessorCalculator {

    public List<Double> computePeriodicDailyReturn(List<PriceModel> closePrices) {
        List<Double> dailyPdr = new LinkedList<>();

        for (int cursor = 0; cursor < closePrices.size() - 1; cursor++) {
            dailyPdr.add(pdr(closePrices.get(cursor).getAdjClose(), closePrices.get(cursor + 1).getAdjClose()));
            closePrices.get(cursor).setPeriodicDailyReturn(pdr(closePrices.get(cursor).getAdjClose(), closePrices.get(cursor + 1).getAdjClose()));
        }
        return dailyPdr;
    }

    private double pdr(double today, double yesterday) {
        //PDR = ln(today price / yesterday price)
        return Math.log(today / yesterday);
    }
}
