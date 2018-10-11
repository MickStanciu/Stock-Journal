package com.example.stockdata.api.impl.service;

import com.example.stockdata.api.impl.calculators.ProbabilityCalculator;
import com.example.stockdata.api.impl.model.SimulatorResult;
import com.example.stockdata.api.spec.model.ProbabilitySimulatorResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimulatorService {

    private static final int SIMULATOR_RUNS = 100;

    public ProbabilitySimulatorResponse simulatePop(double initialAmount, int winValue, int lossValue) {
        List<Double> simulatorWins = new ArrayList<>();
        List<Double> simulatorLoses = new ArrayList<>();
        List<Double> simulatorNoCashToTrade = new ArrayList<>();

        for (int i=0; i<SIMULATOR_RUNS; i++) {
            int[] series = ProbabilityCalculator.generateSeries(70, 100);
            SimulatorResult result = ProbabilityCalculator.simulate(series, initialAmount, 5, winValue, lossValue);

            if (result.isNoMoney()) {
                simulatorNoCashToTrade.add(result.getAmount());
            }

            if (result.getAmount() > initialAmount) {
                simulatorWins.add(result.getAmount());
            } else {
                simulatorLoses.add(result.getAmount());
            }
        }

        double adjustmentRatio = (double) winValue / (100 - (double) winValue);
        double breakEvenStopLoss = Math.round(winValue * adjustmentRatio * 100.0) / 100.0;
        double sweetSpot = Math.round(winValue * 2.19 * 100.0) / 100.0;

        return ProbabilitySimulatorResponse.builder()
                .withWins(simulatorWins)
                .withLoses(simulatorLoses)
                .withNoCashToTrade(simulatorNoCashToTrade)
                .withBreakEvenStopLoss(breakEvenStopLoss)
                .withRecommendedStopLoss(sweetSpot)
                .build();
    }
}
