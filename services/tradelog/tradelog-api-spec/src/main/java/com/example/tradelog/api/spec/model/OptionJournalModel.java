package com.example.tradelog.api.spec.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class OptionJournalModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private String pairTransactionId;
    private String accountId;
    private OffsetDateTime date;
    private String stockSymbol;
    private double stockPrice;
    private double strikePrice;
    private OffsetDateTime expiryDate;
    private double impliedVolatility;
    private double historicalImpliedVolatility;
    private double profitProbability;
    private int contracts;
    private double premium;
    private Action action;
    private ActionType actionType;
    private double brokerFees;
    private String mark;


    public OptionJournalModel() {
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

    public double getHistoricalImpliedVolatility() {
        return historicalImpliedVolatility;
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

    public String getPairTransactionId() {
        return pairTransactionId;
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public OffsetDateTime getExpiryDate() {
        return expiryDate;
    }

    public double getProfitProbability() {
        return profitProbability;
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
        OptionJournalModel model;

        public Builder() {
            model = new OptionJournalModel();
        }

        public OptionJournalModel build() {
            OptionJournalModel buildModel = this.model;
            this.model = new OptionJournalModel();
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

        public Builder withHistoricalImpliedVolatility(double historicalImpliedVolatility) {
            model.historicalImpliedVolatility = historicalImpliedVolatility;
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

        public Builder withPairTransactionId(String pairTransactionId) {
            model.pairTransactionId = pairTransactionId;
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

        public Builder withProfitProbability(double profitProbability) {
            model.profitProbability = profitProbability;
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
