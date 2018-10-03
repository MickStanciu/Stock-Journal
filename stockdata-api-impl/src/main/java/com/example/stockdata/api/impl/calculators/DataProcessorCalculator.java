package com.example.stockdata.api.impl.calculators;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class DataProcessorCalculator {

    public List<Double> computePeriodicDailyReturn(List<Double> closePrices) {
        List<Double> dailyPdr = new LinkedList<>();

        for (int cursor = 0; cursor < closePrices.size() - 1; cursor++) {
            dailyPdr.add(pdr(closePrices.get(cursor), closePrices.get(cursor + 1)));
        }
        return dailyPdr;
    }

    private double pdr(double today, double yesterday) {
        //PDR = ln(today price / yesterday price)
        return Math.log(today / yesterday);
    }
}
