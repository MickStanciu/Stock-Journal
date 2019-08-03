package com.example.tradelog.api.spec.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.io.Serializable;

@JsonDeserialize(builder = TransactionModel.Builder.class)
public class DividendJournalModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private TransactionModel transactionDetails;
    private double dividend;
    private int quantity;

    public TransactionModel getTransactionDetails() {
        return transactionDetails;
    }

    public double getDividend() {
        return dividend;
    }

    public int getQuantity() {
        return quantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "with")
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
            model.transactionDetails = transactionModel;
            return this;
        }

        public Builder withDividend(double dividend) {
            model.dividend = dividend;
            return this;
        }

        public Builder withQuantity(int quantity) {
            model.quantity = quantity;
            return this;
        }
    }
}
