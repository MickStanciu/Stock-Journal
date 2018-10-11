package com.example.stockdata.api.impl.calculators;

import com.example.stockdata.api.impl.model.SimulatorResult;

import java.util.Random;

public class ProbabilityCalculator {

    public static final int WIN = 1;
    public static final int LOSS = 0;

    public static int[] generateSeries(int winRate, int iterations) {
        Random random = new Random();

        int[] series = new int[iterations];

        for (int i=0; i<iterations; i++) {
            if (random.nextInt(100) < winRate) {
                series[i] = 1;
            } else{
                series[i] = 0;
            }
        }

        return series;
    }

    /**
     *
     * @param series
     * @param amount
     * @param riskPercentage
     * @param winValue
     * @param lossValue
     * @return
     */
    public static SimulatorResult simulate(int[] series, double amount, int riskPercentage, int winValue, int lossValue) {
        double investedPc = riskPercentage / 100.0;
        int statWins = 0;
        int statLoses = 0;
        boolean broke = false;

        for (int odd : series) {
            double amount_to_invest = amount * investedPc;
            int contracts = (int) Math.floor(amount_to_invest / lossValue);
            if (contracts == 0) {
                broke = true;
                break;
            }
            if (odd == WIN) {
                statWins++;
                amount += contracts * winValue;
            } else {
                statLoses++;
                amount -= contracts * lossValue;
            }
        }

        return SimulatorResult.builder()
                .withLoses(statLoses)
                .withWins(statWins)
                .withAmount(amount)
                .withNoMoney(broke)
                .build();
    }
}
