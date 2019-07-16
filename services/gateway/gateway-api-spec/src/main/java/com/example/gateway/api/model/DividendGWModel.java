package com.example.gateway.api.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class DividendGWModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private double dividend;
    private String accountId;
    private OffsetDateTime date;
    private String symbol;
    private TransactionTypeGW type;
    private boolean groupEnabled;

    public DividendGWModel() {
        //required by Jackson
    }

    public String getTransactionId() {
        return transactionId;
    }

    public double getDividend() {
        return dividend;
    }

    public String getAccountId() {
        return accountId;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public String getSymbol() {
        return symbol;
    }

    public TransactionTypeGW getType() {
        return type;
    }

    public boolean isGroupEnabled() {
        return groupEnabled;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        DividendGWModel model;

        Builder() {
            model = new DividendGWModel();
        }

        public DividendGWModel build() {
            DividendGWModel buildModel = this.model;
            this.model = new DividendGWModel();
            return buildModel;
        }

        public Builder withTransactionId(String transactionId) {
            model.transactionId = transactionId;
            return this;
        }

        public Builder withDividend(double dividend) {
            model.dividend = dividend;
            return this;
        }

        public Builder withAccountId(String accountId) {
            model.accountId = accountId;
            return this;
        }

        public Builder withDate(OffsetDateTime date) {
            model.date = date;
            return this;
        }

        public Builder withSymbol(String symbol) {
            model.symbol = symbol;
            return this;
        }

        public Builder withType(TransactionTypeGW type) {
            model.type = type;
            return this;
        }

        public Builder withGroupEnabled(boolean groupEnabled) {
            model.groupEnabled = groupEnabled;
            return this;
        }
    }
}
