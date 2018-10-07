package com.example.stockdata.api.impl.calculators;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MathUtilsTest {

    @Test
    public void testMean() {
        double[] input = {4, 5};
        double mean = MathUtils.mean(input);
        Assert.assertEquals(mean, 4.5);
    }

    @Test
    public void testPdr() {
        double pdr = MathUtils.pdr(13.12, 12.45);
        Assert.assertEquals(pdr, 0.052417160605226325);
    }
}
