package com.example.gateway.api.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class ShareJournalGWModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private String accountId;
    private OffsetDateTime date;
    private String symbol;
    private double price;
    private int quantity;
    private ActionGW action;
    private TransactionTypeGW type;
    private double brokerFees;

    public ShareJournalGWModel() {
        //required by Jackson
    }

    public String getTransactionId() {
        return transactionId;
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

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public ActionGW getAction() {
        return action;
    }

    public TransactionTypeGW getType() {
        return type;
    }

    public double getBrokerFees() {
        return brokerFees;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        ShareJournalGWModel model;

        Builder() {
            model = new ShareJournalGWModel();
        }

        public ShareJournalGWModel build() {
            ShareJournalGWModel buildModel = this.model;
            this.model = new ShareJournalGWModel();
            return buildModel;
        }

        public Builder withTransactionId(String transactionId) {
            model.transactionId = transactionId;
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

        public Builder withPrice(double price) {
            model.price = price;
            return this;
        }

        public Builder withAction(ActionGW actionGW) {
            model.action = actionGW;
            return this;
        }

        public Builder withType(TransactionTypeGW type) {
            model.type = type;
            return this;
        }

        public Builder withBrokerFees(double brokerFees) {
            model.brokerFees = brokerFees;
            return this;
        }

        public Builder withQuantity(int quantity) {
            model.quantity = quantity;
            return this;
        }
    }
}
