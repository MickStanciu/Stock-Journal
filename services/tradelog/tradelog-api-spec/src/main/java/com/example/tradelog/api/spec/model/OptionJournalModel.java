package com.example.tradelog.api.spec.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class OptionJournalModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private TransactionModel transactionDetails;
    private double stockPrice;
    private double strikePrice;
    private OffsetDateTime expiryDate;
    private int contracts;
    private double premium;
    private Action action;
    private ActionType actionType;
    private double brokerFees;


    public OptionJournalModel() {
        //required by Jackson
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

    public double getStockPrice() {
        return stockPrice;
    }

    public TransactionModel getTransactionDetails() {
        return transactionDetails;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        OptionJournalModel model;

        public Builder() {
            model = new OptionJournalModel();
        }

        public OptionJournalModel build() {
            OptionJournalModel buildModel = this.model;
            this.model = new OptionJournalModel();
            return buildModel;
        }

        public Builder withTransactionModel(TransactionModel transactionModel) {
            model.transactionDetails = transactionModel;
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

        public Builder withStockPrice(double stockPrice) {
            model.stockPrice = stockPrice;
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
