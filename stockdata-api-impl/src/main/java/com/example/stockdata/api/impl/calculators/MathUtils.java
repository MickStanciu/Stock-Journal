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

    /**
     * Calculates the standard deviation for a given array
     * (SUM(Xi - Xmean)^2)/(n-1)
     * @param observations - array of double
     * @return computed std
     */
    public static double standardDeviation(double[] observations) {
        // 1. calculate mean
        double mean = mean(observations);

        // 2. SUM(Xi - mean)^2
        double sqaredDiff = 0;
        for (double value : observations) {
            sqaredDiff += (value - mean) * (value - mean);
        }

        // 3. SUM(Xi - mean)^2 / n - 1
        return Math.sqrt(sqaredDiff / (observations.length - 1));
    }

    /**
     * Calculates mean (average)
     * (SUM(Xi))/n
     * @param values - numbers to calculate
     * @return mean
     */
    public static double mean(double[] values) {
        double sum = Arrays.stream(values).sum();
        return sum / values.length;
    }
}
