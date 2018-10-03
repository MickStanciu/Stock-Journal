package com.example.stockdata.api.spec.model;

import java.time.LocalDate;

public class PriceModel {

    private LocalDate date;
    private String symbol;
    private double adjClose;
    private boolean processed;
    private double periodicDailyReturn;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(double adjClose) {
        this.adjClose = adjClose;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public double getPeriodicDailyReturn() {
        return periodicDailyReturn;
    }

    public void setPeriodicDailyReturn(double periodicDailyReturn) {
        this.periodicDailyReturn = periodicDailyReturn;
    }

    public static class Builder {
        PriceModel priceModel;

        public Builder() {
            priceModel = new PriceModel();
        }

        public PriceModel build() {
            return priceModel;
        }

        public Builder withAdjClose(double price) {
            priceModel.setAdjClose(price);
            return this;
        }
    }
}
