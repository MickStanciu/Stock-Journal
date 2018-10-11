package com.example.stockdata.api.impl.calculators;

import com.example.stockdata.api.impl.model.SimulatorResult;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


public class ProbabilityCalculatorTest {



    @Test
    public void testGenerator() {
        int[] series = ProbabilityCalculator.generateSeries(70, 10000);
        int sum = IntStream.of(series).sum() / 100;
        Assert.assertTrue(sum >= 68);
        Assert.assertTrue(sum <= 72);
    }

    @Test
    public void testTrade() {
        int simulatorRuns = 100;
        List<Double> simulatorWins = new ArrayList<>();
        List<Double> simulatorLoses = new ArrayList<>();
        List<Double> simulatorNoCashToTrade = new ArrayList<>();

        for (int i=0; i<simulatorRuns; i++) {
            int[] series = ProbabilityCalculator.generateSeries(70, 100);
            double initialAmount = 20000.0;
            int winValue = 70;
            int lossValue = 163;
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

        System.out.println("Trading results for #" + simulatorRuns + " iterations:");
        System.out.println("  system won     " + simulatorWins.size() + " times");
        System.out.println("  system lost    " + simulatorLoses.size() + " times");
        System.out.println("  system stopped " + simulatorNoCashToTrade.size() + " times");
        System.out.println();

        Optional<Double> maxWin = simulatorWins.stream().reduce(Double::max);
        maxWin.ifPresent(aDouble -> System.out.println("Biggest win amount: " + aDouble));

        Optional<Double> maxLoss = simulatorLoses.stream().reduce(Double::max);
        maxLoss.ifPresent(aDouble -> System.out.println("Biggest loss amount: " + aDouble));

        System.out.println();
        double wins = simulatorWins.stream().mapToDouble(Double::doubleValue).sum();
        double loses = simulatorLoses.stream().mapToDouble(Double::doubleValue).sum();
        System.out.println("Account value: " + (int) (wins - loses));
    }
}
