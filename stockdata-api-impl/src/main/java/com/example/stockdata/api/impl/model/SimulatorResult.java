package com.example.stockdata.api.impl.model;

public class SimulatorResult {
    private int wins = 0;
    private int loses = 0;
    private double amount = 0;
    private boolean noMoney = false;

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isNoMoney() {
        return noMoney;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private SimulatorResult result;

        public Builder() {
            this.result = new SimulatorResult();
        }

        public Builder withWins(int wins) {
            result.wins = wins;
            return this;
        }

        public Builder withLoses(int loses) {
            result.loses = loses;
            return this;
        }

        public Builder withAmount(double amount){
            result.amount = amount;
            return this;
        }

        public Builder withNoMoney(boolean flag) {
            result.noMoney = flag;
            return this;
        }

        public SimulatorResult build() {
            return result;
        }
    }
}
