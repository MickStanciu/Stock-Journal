package com.example.stockdata.api.spec.model;

import java.util.List;

public class ProbabilitySimulatorResponse {

    private List<Double> simulatorWins;
    private List<Double> simulatorLoses;
    private List<Double> simulatorNoCashToTrade;
    private double breakEvenStopLoss;
    private double recommendedStopLoss;

    public List<Double> getSimulatorWins() {
        return simulatorWins;
    }

    public List<Double> getSimulatorLoses() {
        return simulatorLoses;
    }

    public List<Double> getSimulatorNoCashToTrade() {
        return simulatorNoCashToTrade;
    }

    public double getBreakEvenStopLoss() {
        return breakEvenStopLoss;
    }

    public double getRecommendedStopLoss() {
        return recommendedStopLoss;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        ProbabilitySimulatorResponse response;

        public Builder() {
            response = new ProbabilitySimulatorResponse();
        }

        public Builder withWins(List<Double> wins) {
            response.simulatorWins = wins;
            return this;
        }

        public Builder withLoses(List<Double> loses) {
            response.simulatorLoses = loses;
            return this;
        }

        public Builder withNoCashToTrade(List<Double> noCashToTrade) {
            response.simulatorNoCashToTrade = noCashToTrade;
            return this;
        }

        public Builder withBreakEvenStopLoss(double breakEvenStopLoss) {
            response.breakEvenStopLoss = breakEvenStopLoss;
            return this;
        }

        public Builder withRecommendedStopLoss(double recommendedStopLoss) {
            response.recommendedStopLoss = recommendedStopLoss;
            return this;
        }


        public ProbabilitySimulatorResponse build() {
            return response;
        }
    }
}
