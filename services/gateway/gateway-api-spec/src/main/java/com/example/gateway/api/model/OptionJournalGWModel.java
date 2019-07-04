package com.example.gateway.api.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class OptionJournalGWModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private String accountId;
    private OffsetDateTime date;
    private String stockSymbol;
    private double stockPrice;
    private double strikePrice;
    private OffsetDateTime expiryDate;
    private int contracts;
    private double premium;
    private ActionGW action;
    private OptionTypeGW optionType;
    private TransactionTypeGW type;
    private double brokerFees;


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

    public String getStockSymbol() {
        return stockSymbol;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public ActionGW getAction() {
        return action;
    }

    public OptionTypeGW getOptionType() {
        return optionType;
    }

    public TransactionTypeGW getType() {
        return type;
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

        public Builder withStockSymbol(String stockSymbol) {
            model.stockSymbol = stockSymbol;
            return this;
        }

        public Builder withStockPrice(double stockPrice) {
            model.stockPrice = stockPrice;
            return this;
        }

        public Builder withAction(ActionGW actionGW) {
            model.action = actionGW;
            return this;
        }

        public Builder withOptionType(OptionTypeGW optionTypeGW) {
            model.optionType = optionTypeGW;
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

        public Builder withType(TransactionTypeGW type) {
            model.type = type;
            return this;
        }
    }
}
