package com.example.tradelog.api.spec.model;

import java.time.OffsetDateTime;

public class ShareJournalModel {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private String accountId;
    private OffsetDateTime date;
    private String symbol;
    private double price;
    private int quantity;
    private Action action;
    private ActionType actionType;
    private double brokerFees;
    private String mark;

    public ShareJournalModel() {
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

    public Action getAction() {
        return action;
    }

    public ActionType getActionType() {
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
        ShareJournalModel model;

        public Builder() {
            model = new ShareJournalModel();
        }

        public ShareJournalModel build() {
            ShareJournalModel buildModel = this.model;
            this.model = new ShareJournalModel();
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

        public Builder withAction(Action action) {
            model.action = action;
            return this;
        }

        public Builder withActionType(ActionType actionType) {
            model.actionType = actionType;
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
