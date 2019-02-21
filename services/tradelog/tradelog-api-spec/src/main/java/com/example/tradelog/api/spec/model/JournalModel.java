package com.example.tradelog.api.spec.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class JournalModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private String accountId;
    private OffsetDateTime date;
    private String mark;

    private String stockSymbol;
    private float stockPrice;
    private float impliedVolatility;
    private float historicalImpliedVolatility;

    private Action action;
    private ActionType actionType;

    private float brokerFees;

    public JournalModel() {
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

    public float getStockPrice() {
        return stockPrice;
    }

    public float getImpliedVolatility() {
        return impliedVolatility;
    }

    public float getHistoricalImpliedVolatility() {
        return historicalImpliedVolatility;
    }

    public Action getAction() {
        return action;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public float getBrokerFees() {
        return brokerFees;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        JournalModel journalModel;

        Builder() {
            journalModel = new JournalModel();
        }

        public JournalModel build() {
            JournalModel buildModel = this.journalModel;
            this.journalModel = new JournalModel();
            return buildModel;
        }

        public Builder withTransactionId(String transactionId) {
            journalModel.transactionId = transactionId;
            return this;
        }

        public Builder withAccountId(String accountId) {
            journalModel.accountId = accountId;
            return this;
        }

        public Builder withDate(OffsetDateTime date) {
            journalModel.date = date;
            return this;
        }

        public Builder withMark(String mark) {
            journalModel.mark = mark;
            return this;
        }

        public Builder withStockSymbol(String stockSymbol) {
            journalModel.stockSymbol = stockSymbol;
            return this;
        }

        public Builder withStockPrice(float stockPrice) {
            journalModel.stockPrice = stockPrice;
            return this;
        }

        public Builder withImpliedVolatility(float impliedVolatility) {
            journalModel.impliedVolatility = impliedVolatility;
            return this;
        }

        public Builder withHistoricalImpliedVolatility(float historicalImpliedVolatility) {
            journalModel.historicalImpliedVolatility = historicalImpliedVolatility;
            return this;
        }

        public Builder withAction(Action action) {
            journalModel.action = action;
            return this;
        }

        public Builder withActionType(ActionType actionType) {
            journalModel.actionType = actionType;
            return this;
        }

        public Builder withBrokerFees(float brokerFees) {
            journalModel.brokerFees = brokerFees;
            return this;
        }
    }
}
