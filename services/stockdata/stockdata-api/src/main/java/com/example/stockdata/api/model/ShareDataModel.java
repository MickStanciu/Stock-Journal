package com.example.stockdata.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


public class ShareDataModel {
    private String symbol;
    private OffsetDateTime lastUpdatedOn;
    private String sector;
    private double price;
    private double marketCapitalization;
    private double peRatio;
    private double peRatioFuture;
    private double bookValue;
    private double eps;
    private double epsFuture;
    private BigDecimal finvizTarget;
    private BigDecimal calculatedTarget;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public OffsetDateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(OffsetDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMarketCapitalization() {
        return marketCapitalization;
    }

    public void setMarketCapitalization(double marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    public double getPeRatio() {
        return peRatio;
    }

    public void setPeRatio(double peRatio) {
        this.peRatio = peRatio;
    }

    public double getPeRatioFuture() {
        return peRatioFuture;
    }

    public void setPeRatioFuture(double peRatioFuture) {
        this.peRatioFuture = peRatioFuture;
    }

    public double getBookValue() {
        return bookValue;
    }

    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }

    public double getEps() {
        return eps;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public double getEpsFuture() {
        return epsFuture;
    }

    public void setEpsFuture(double epsFuture) {
        this.epsFuture = epsFuture;
    }

    public BigDecimal getFinvizTarget() {
        return finvizTarget;
    }

    public void setFinvizTarget(BigDecimal finvizTarget) {
        this.finvizTarget = finvizTarget;
    }

    public BigDecimal getCalculatedTarget() {
        return calculatedTarget;
    }

    public void setCalculatedTarget(BigDecimal calculatedTarget) {
        this.calculatedTarget = calculatedTarget;
    }
}
