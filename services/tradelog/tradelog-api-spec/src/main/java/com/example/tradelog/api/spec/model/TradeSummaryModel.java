package com.example.tradelog.api.spec.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TradeSummaryModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String symbol;
    private int trades;
    private BigDecimal total;

    public TradeSummaryModel() {
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

        public Builder withTotal(BigDecimal total) {
            tradeSummaryModel.total = total;
            return this;
        }
    }
}
