package com.example.gateway.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ShareDataGWModel implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public ShareDataGWModel() {
        //required by Jackson
    }

    public String getSymbol() {
        return symbol;
    }

    public OffsetDateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public String getSector() {
        return sector;
    }

    public double getPrice() {
        return price;
    }

    public double getMarketCapitalization() {
        return marketCapitalization;
    }

    public double getPeRatio() {
        return peRatio;
    }

    public double getPeRatioFuture() {
        return peRatioFuture;
    }

    public double getBookValue() {
        return bookValue;
    }

    public double getEps() {
        return eps;
    }

    public double getEpsFuture() {
        return epsFuture;
    }

    public BigDecimal getFinvizTarget() {
        return finvizTarget;
    }

    public BigDecimal getCalculatedTarget() {
        return calculatedTarget;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        ShareDataGWModel model;

        public Builder() {
            model = new ShareDataGWModel();
        }

        public ShareDataGWModel build() {
            ShareDataGWModel buildModel = this.model;
            this.model = new ShareDataGWModel();
            return buildModel;
        }

        public Builder withSymbol(String symbol) {
            model.symbol = symbol;
            return this;
        }

        public Builder withLastUpdatedOn(OffsetDateTime lastUpdatedOn) {
            model.lastUpdatedOn = lastUpdatedOn;
            return this;
        }

        public Builder withSector(String sector) {
            model.sector = sector;
            return this;
        }

        public Builder withPrice(double price) {
            model.price = price;
            return this;
        }

        public Builder withMarketCapitalization(double marketCapitalization) {
            model.marketCapitalization = marketCapitalization;
            return this;
        }

        public Builder withPeRatio(double peRatio) {
            model.peRatio = peRatio;
            return this;
        }

        public Builder withPeRatioFuture(double peRatioFuture) {
            model.peRatioFuture = peRatioFuture;
            return this;
        }

        public Builder withBookValue(double bookValue) {
            model.bookValue = bookValue;
            return this;
        }

        public Builder withEps(double eps) {
            model.eps = eps;
            return this;
        }

        public Builder withEpsFuture(double epsFuture) {
            model.epsFuture = epsFuture;
            return this;
        }

        public Builder withFinvizTarget(BigDecimal finvizTarget) {
            model.finvizTarget = finvizTarget;
            return this;
        }

        public Builder withCalculatedTarget(BigDecimal calculatedTarget) {
            model.calculatedTarget = calculatedTarget;
            return this;
        }
    }
}
