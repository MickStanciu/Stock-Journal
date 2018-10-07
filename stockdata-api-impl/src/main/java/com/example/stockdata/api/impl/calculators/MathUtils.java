package com.example.stockdata.api.impl.calculators;

import java.util.Arrays;

public class MathUtils {
    /**
     * Calculates Periodic Daily Return
     * @param today - today value
     * @param yesterday - yesterday value
     * @return pdr
     */
    public static double pdr(double today, double yesterday) {
        //PDR = ln(today price / yesterday price)
        return Math.log(today / yesterday);
    }

    public static double standardDeviation(double[] values) {

        return 0;
    }

    /**
     * Calculates mean (average)
     * @param values - numbers to calculate
     * @return mean
     */
    public static double mean(double[] values) {
        double sum = Arrays.stream(values).sum();
        return sum / values.length;
    }
}
