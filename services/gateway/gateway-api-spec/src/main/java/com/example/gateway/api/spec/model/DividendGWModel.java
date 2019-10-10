package com.example.gateway.api.spec.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.io.Serializable;
import java.time.OffsetDateTime;

@JsonDeserialize(builder = DividendGWModel.Builder.class)
public class DividendGWModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private double dividend;
    private int quantity;
    private String accountId;
    private OffsetDateTime date;
    private String symbol;
    private TransactionTypeGW type;
    private boolean groupSelected;
    private boolean legClosed;

    public String getTransactionId() {
        return transactionId;
    }

    public double getDividend() {
        return dividend;
    }

    public int getQuantity() {
        return quantity;
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

    public boolean isGroupSelected() {
        return groupSelected;
    }

    public boolean isLegClosed() {
        return legClosed;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "with")
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

        public Builder withQuantity(int quantity) {
            model.quantity = quantity;
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

        public Builder withGroupSelected(boolean groupSelected) {
            model.groupSelected = groupSelected;
            return this;
        }

        public Builder withLegClosed(boolean legClosed) {
            model.legClosed = legClosed;
            return this;
        }
    }
}
