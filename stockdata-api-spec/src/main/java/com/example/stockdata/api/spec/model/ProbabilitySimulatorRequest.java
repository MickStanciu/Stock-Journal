package com.example.stockdata.api.spec.model;

public class ProbabilitySimulatorRequest {
    private double initialAmount;
    private int winValue;
    private int lossValue;

    public ProbabilitySimulatorRequest() {
        //jackson
    }

    public double getInitialAmount() {
        return initialAmount;
    }

    public int getWinValue() {
        return winValue;
    }

    public int getLossValue() {
        return lossValue;
    }
}
