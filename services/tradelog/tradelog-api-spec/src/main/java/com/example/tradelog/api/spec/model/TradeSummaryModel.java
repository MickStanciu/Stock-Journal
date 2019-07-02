package com.example.tradelog.api.spec.model;

public class TradeSummaryModel {
    private String symbol;
    private int trades;
    private double total;

    public TradeSummaryModel() {
        //required by Jackson
    }

    public String getSymbol() {
        return symbol;
    }

    public int getTrades() {
        return trades;
    }

    public double getTotal() {
        return total;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        TradeSummaryModel tradeSummaryModel;

        Builder() {
            tradeSummaryModel = new TradeSummaryModel();
        }

        public TradeSummaryModel build() {
            TradeSummaryModel buildModel = this.tradeSummaryModel;
            this.tradeSummaryModel = new TradeSummaryModel();
            return buildModel;
        }

        public Builder withSymbol(String symbol) {
            tradeSummaryModel.symbol = symbol;
            return this;
        }

        public Builder withTrades(int trades) {
            tradeSummaryModel.trades = trades;
            return this;
        }

        public Builder withTotal(double total) {
            tradeSummaryModel.total = total;
            return this;
        }
    }
}
