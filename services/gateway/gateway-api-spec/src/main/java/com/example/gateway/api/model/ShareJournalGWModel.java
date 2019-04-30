package com.example.gateway.api.model;

import java.time.OffsetDateTime;

public class ShareJournalGWModel {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private String accountId;
    private OffsetDateTime date;
    private String symbol;
    private double price;
    private int quantity;
    private ActionGW action;
    private ActionTypeGW actionType;
    private double brokerFees;
    private String mark;

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

    public ActionTypeGW getActionType() {
        return actionType;
    }

    public double getBrokerFees() {
        return brokerFees;
    }

    public String getMark() {
        return mark;
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

        public Builder withMark(String mark) {
            model.mark = mark;
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

        public Builder withActionType(ActionTypeGW actionTypeGW) {
            model.actionType = actionTypeGW;
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
