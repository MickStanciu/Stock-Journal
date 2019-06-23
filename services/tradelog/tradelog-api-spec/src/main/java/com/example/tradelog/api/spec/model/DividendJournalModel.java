package com.example.tradelog.api.spec.model;

public class DividendJournalModel {

    private static final long serialVersionUID = 1L;

    private TransactionModel transactionModel;
    private double dividend;

    public DividendJournalModel() {
        //required by Jackson
    }

    public TransactionModel getTransactionModel() {
        return transactionModel;
    }

    public double getDividend() {
        return dividend;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        DividendJournalModel model;

        public Builder() {
            model = new DividendJournalModel();
        }

        public DividendJournalModel build() {
            DividendJournalModel buildModel = this.model;
            this.model = new DividendJournalModel();
            return buildModel;
        }

        public Builder withTransactionModel(TransactionModel transactionModel) {
            model.transactionModel = transactionModel;
            return this;
        }

        public Builder withDividend(double dividend) {
            model.dividend = dividend;
            return this;
        }
    }
}
