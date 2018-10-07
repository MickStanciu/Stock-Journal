package com.example.stockdata.api.impl.calculators;

import com.example.stockdata.api.spec.model.PriceModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataProcessorCalculator {

    public void computePeriodicDailyReturn(List<PriceModel> closePrices) {
        for (int cursor = 0; cursor < closePrices.size() - 1; cursor++) {
            closePrices.get(cursor).setPeriodicDailyReturn(MathUtils.pdr(closePrices.get(cursor).getAdjClose(), closePrices.get(cursor + 1).getAdjClose()));
        }
    }

    public double computeStandardDeviation(List<PriceModel> closePrice) {
        // 1. calculate mean
        double[] arr = closePrice.stream().mapToDouble(PriceModel::getPeriodicDailyReturn).toArray();
        return MathUtils.standardDeviation(arr);
    }
}
