package com.example.stockdata.api.impl.calculators;

import com.example.stockdata.api.spec.model.PriceModel;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class DataProcessorCalculatorTest {

    private DataProcessorCalculator calculator;

    private List<PriceModel> prices;

    @BeforeClass
    public void setUp() {
        calculator = new DataProcessorCalculator();

        prices = new LinkedList<>();
        prices.add(new PriceModel.Builder().withAdjClose(100.709763).build());
        prices.add(new PriceModel.Builder().withAdjClose(100.298927).build());
        prices.add(new PriceModel.Builder().withAdjClose(99.271721).build());
        prices.add(new PriceModel.Builder().withAdjClose(99.934723).build());
        prices.add(new PriceModel.Builder().withAdjClose(98.767479).build());
        prices.add(new PriceModel.Builder().withAdjClose(97.170677).build());
        prices.add(new PriceModel.Builder().withAdjClose(96.778488).build());
        prices.add(new PriceModel.Builder().withAdjClose(99.112991).build());
        prices.add(new PriceModel.Builder().withAdjClose(100.551018).build());
        prices.add(new PriceModel.Builder().withAdjClose(101.055275).build());
    }

    @Test
    public void testPeriodicDailyReturn() {
        calculator.computePeriodicDailyReturn(prices);
//        Assert.assertEquals(computedDailyReturns.size(), prices.size() - 1);

        Assert.assertEquals(prices.get(0).getPeriodicDailyReturn(), 0.004087749361268957);
        Assert.assertEquals(prices.get(1).getPeriodicDailyReturn(), 0.010294249996166429);
        Assert.assertEquals(prices.get(2).getPeriodicDailyReturn(), -0.0066564558327733775);
        Assert.assertEquals(prices.get(3).getPeriodicDailyReturn(), 0.01174881219121577);
        Assert.assertEquals(prices.get(4).getPeriodicDailyReturn(), 0.016299401651762375);
        Assert.assertEquals(prices.get(5).getPeriodicDailyReturn(), 0.004044250817499626);
        Assert.assertEquals(prices.get(6).getPeriodicDailyReturn(), -0.02383578437218368);
        Assert.assertEquals(prices.get(7).getPeriodicDailyReturn(), -0.014404717930972406);
        Assert.assertEquals(prices.get(8).getPeriodicDailyReturn(), -0.005002403883745457);
    }

}
