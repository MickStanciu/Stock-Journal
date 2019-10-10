package com.example.gateway.api.spec.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TradeSummaryGWModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String symbol;
    private int trades;
    private BigDecimal total;

    public TradeSummaryGWModel() {
        //required by Jackson
    }

    public String getSymbol() {
        return symbol;
    }

    public int getTrades() {
        return trades;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        TradeSummaryGWModel model;

        Builder() {
            model = new TradeSummaryGWModel();
        }

        public TradeSummaryGWModel build() {
            TradeSummaryGWModel buildModel = this.model;
            this.model = new TradeSummaryGWModel();
            return buildModel;
        }

        public Builder withSymbol(String symbol) {
            model.symbol = symbol;
            return this;
        }

        public Builder withTrades(int trades) {
            model.trades = trades;
            return this;
        }

        public Builder withTotal(BigDecimal total) {
            model.total = total;
            return this;
        }
    }
}
