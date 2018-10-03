package com.example.stockdata.api.impl.calculators;

import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class DataProcessorCalculatorTest {

    private DataProcessorCalculator calculator;

    private List<Double> closingPrices;

    @BeforeClass
    public void setUp() {
        calculator = new DataProcessorCalculator();

        closingPrices = new LinkedList<>();
        closingPrices.add(100.709763);
        closingPrices.add(100.298927);
        closingPrices.add(99.271721);
        closingPrices.add(99.934723);
        closingPrices.add(98.767479);
        closingPrices.add(97.170677);
        closingPrices.add(96.778488);
        closingPrices.add(99.112991);
        closingPrices.add(100.551018);
        closingPrices.add(101.055275);
    }

    @Test
    public void testPeriodicDailyReturn() {
        List<Double> computedDailyReturns = calculator.computePeriodicDailyReturn(closingPrices);
        Assert.assertEquals(computedDailyReturns.size(), closingPrices.size() - 1);

        Assert.assertEquals(computedDailyReturns.get(0), 0.004087749361268957);
        Assert.assertEquals(computedDailyReturns.get(1), 0.010294249996166429);
        Assert.assertEquals(computedDailyReturns.get(2), -0.0066564558327733775);
        Assert.assertEquals(computedDailyReturns.get(3), 0.01174881219121577);
        Assert.assertEquals(computedDailyReturns.get(4), 0.016299401651762375);
        Assert.assertEquals(computedDailyReturns.get(5), 0.004044250817499626);
        Assert.assertEquals(computedDailyReturns.get(6), -0.02383578437218368);
        Assert.assertEquals(computedDailyReturns.get(7), -0.014404717930972406);
        Assert.assertEquals(computedDailyReturns.get(8), -0.005002403883745457);
    }

}
