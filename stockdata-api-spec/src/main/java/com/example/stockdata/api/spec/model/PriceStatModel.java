package com.example.stockdata.api.spec.model;

import java.time.LocalDate;

public class PriceStatModel {

    private LocalDate date;
    private String symbol;
    private double std;

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

    public double getStd() {
        return std;
    }

    public void setStd(double std) {
        this.std = std;
    }
}
