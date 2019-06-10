package com.example.gateway.api.model;

import java.time.OffsetDateTime;

public class OptionJournalGWModel {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private String accountId;
    private OffsetDateTime date;
    private String stockSymbol;
    private double stockPrice;
    private double strikePrice;
    private OffsetDateTime expiryDate;
    private double impliedVolatility;
    private int contracts;
    private double premium;
    private ActionGW action;
    private ActionTypeGW actionType;
    private double brokerFees;
    private String mark;


    public OptionJournalGWModel() {
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

    public String getMark() {
        return mark;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public double getImpliedVolatility() {
        return impliedVolatility;
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

    public double getStrikePrice() {
        return strikePrice;
    }

    public OffsetDateTime getExpiryDate() {
        return expiryDate;
    }

    public int getContracts() {
        return contracts;
    }

    public double getPremium() {
        return premium;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        OptionJournalGWModel model;

        Builder() {
            model = new OptionJournalGWModel();
        }

        public OptionJournalGWModel build() {
            OptionJournalGWModel buildModel = this.model;
            this.model = new OptionJournalGWModel();
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

        public Builder withStockSymbol(String stockSymbol) {
            model.stockSymbol = stockSymbol;
            return this;
        }

        public Builder withStockPrice(double stockPrice) {
            model.stockPrice = stockPrice;
            return this;
        }

        public Builder withImpliedVolatility(double impliedVolatility) {
            model.impliedVolatility = impliedVolatility;
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

        public Builder withStrikePrice(double strikePrice) {
            model.strikePrice = strikePrice;
            return this;
        }

        public Builder withExpiryDate(OffsetDateTime expiryDate) {
            model.expiryDate = expiryDate;
            return this;
        }

        public Builder withContracts(int contracts) {
            model.contracts = contracts;
            return this;
        }

        public Builder withPremium(double premium) {
            model.premium = premium;
            return this;
        }
    }
}
