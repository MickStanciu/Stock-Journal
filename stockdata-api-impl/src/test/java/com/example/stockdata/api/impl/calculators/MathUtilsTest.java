package com.example.stockdata.api.impl.calculators;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MathUtilsTest {

    @Test
    public void testMean() {
        double[] input = {
                17,
                15,
                23,
                7,
                9,
                13};
        double mean = MathUtils.mean(input);
        Assert.assertEquals(mean, 14.0);
    }

    @Test
    public void testPdr() {
        double pdr = MathUtils.pdr(13.12, 12.45);
        Assert.assertEquals(pdr, 0.052417160605226325);
    }

    @Test
    public void testStandardDeviation() {
        double[] input = {
                17,
                15,
                23,
                7,
                9,
                13};

        double std = MathUtils.standardDeviation(input);
        Assert.assertEquals(std, 5.761944116355173);
    }
}
