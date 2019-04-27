package com.example.gateway.api.model;

import java.time.OffsetDateTime;

public class OptionJournalGWModel {

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
    private ActionGW actionGW;
    private ActionTypeGW actionTypeGW;
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

    public double getHistoricalImpliedVolatility() {
        return historicalImpliedVolatility;
    }

    public ActionGW getActionGW() {
        return actionGW;
    }

    public ActionTypeGW getActionTypeGW() {
        return actionTypeGW;
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
        OptionJournalGWModel optionJournalModel;

        Builder() {
            optionJournalModel = new OptionJournalGWModel();
        }

        public OptionJournalGWModel build() {
            OptionJournalGWModel buildModel = this.optionJournalModel;
            this.optionJournalModel = new OptionJournalGWModel();
            return buildModel;
        }

        public Builder withTransactionId(String transactionId) {
            optionJournalModel.transactionId = transactionId;
            return this;
        }

        public Builder withAccountId(String accountId) {
            optionJournalModel.accountId = accountId;
            return this;
        }

        public Builder withDate(OffsetDateTime date) {
            optionJournalModel.date = date;
            return this;
        }

        public Builder withMark(String mark) {
            optionJournalModel.mark = mark;
            return this;
        }

        public Builder withStockSymbol(String stockSymbol) {
            optionJournalModel.stockSymbol = stockSymbol;
            return this;
        }

        public Builder withStockPrice(double stockPrice) {
            optionJournalModel.stockPrice = stockPrice;
            return this;
        }

        public Builder withImpliedVolatility(double impliedVolatility) {
            optionJournalModel.impliedVolatility = impliedVolatility;
            return this;
        }

        public Builder withHistoricalImpliedVolatility(double historicalImpliedVolatility) {
            optionJournalModel.historicalImpliedVolatility = historicalImpliedVolatility;
            return this;
        }

        public Builder withAction(ActionGW actionGW) {
            optionJournalModel.actionGW = actionGW;
            return this;
        }

        public Builder withActionType(ActionTypeGW actionTypeGW) {
            optionJournalModel.actionTypeGW = actionTypeGW;
            return this;
        }

        public Builder withBrokerFees(double brokerFees) {
            optionJournalModel.brokerFees = brokerFees;
            return this;
        }

        public Builder withPairTransactionId(String pairTransactionId) {
            optionJournalModel.pairTransactionId = pairTransactionId;
            return this;
        }

        public Builder withStrikePrice(double strikePrice) {
            optionJournalModel.strikePrice = strikePrice;
            return this;
        }

        public Builder withExpiryDate(OffsetDateTime expiryDate) {
            optionJournalModel.expiryDate = expiryDate;
            return this;
        }

        public Builder withProfitProbability(double profitProbability) {
            optionJournalModel.profitProbability = profitProbability;
            return this;
        }

        public Builder withContracts(int contracts) {
            optionJournalModel.contracts = contracts;
            return this;
        }

        public Builder withPremium(double premium) {
            optionJournalModel.premium = premium;
            return this;
        }
    }
}
