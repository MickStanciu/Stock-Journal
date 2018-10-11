package com.example.stockdata.api.impl.calculators;

import com.example.stockdata.api.impl.service.SimulatorService;
import com.example.stockdata.api.spec.model.ProbabilitySimulatorResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.stream.IntStream;


public class ProbabilityCalculatorTest {

    private SimulatorService simulatorService;

    @BeforeMethod
    public void setUp() {
        simulatorService = new SimulatorService();
    }

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

        double initialAmount = 10000.0;
        int winValue = 70;
        int lossValue = 153;

        ProbabilitySimulatorResponse response = simulatorService.simulatePop(initialAmount, winValue, lossValue);


        System.out.println("Calculated break/even price: " + response.getBreakEvenStopLoss());
        System.out.println("Recommended roll price:      " + response.getRecommendedStopLoss());
        System.out.println();

        System.out.println("Trading results for #" + simulatorRuns + " iterations:");
        System.out.println("  system won       " + response.getSimulatorWins().size() + " times");
        System.out.println("  system lost      " + response.getSimulatorLoses().size() + " times");

        double ratio = Math.round ((response.getSimulatorWins().size() * 100.0)/ response.getSimulatorLoses().size() ) / 100.0;

        System.out.println("  system w/l ratio " + ratio + ":1");
        System.out.println("  system stopped " + response.getSimulatorNoCashToTrade().size() + " times");
        System.out.println();

        Optional<Double> maxWin = response.getSimulatorWins().stream().reduce(Double::max);
        maxWin.ifPresent(aDouble -> System.out.println("Biggest win amount: " + aDouble));

        Optional<Double> maxLoss = response.getSimulatorLoses().stream().reduce(Double::max);
        maxLoss.ifPresent(aDouble -> System.out.println("Biggest loss amount: " + aDouble));

        System.out.println();
        double wins = response.getSimulatorWins().stream().mapToDouble(Double::doubleValue).sum();
        double loses = response.getSimulatorLoses().stream().mapToDouble(Double::doubleValue).sum();
        System.out.println("Account value: " + (int) (wins - loses));
    }
}
