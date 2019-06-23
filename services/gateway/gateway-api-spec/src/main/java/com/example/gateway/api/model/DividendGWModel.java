package com.example.gateway.api.model;

public class DividendGWModel {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private double dividend;

    public DividendGWModel() {
        //required by Jackson
    }

    public String getTransactionId() {
        return transactionId;
    }

    public double getDividend() {
        return dividend;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        DividendGWModel model;

        Builder() {
            model = new DividendGWModel();
        }

        public DividendGWModel build() {
            DividendGWModel buildModel = this.model;
            this.model = new DividendGWModel();
            return buildModel;
        }

        public Builder withTransactionId(String transactionId) {
            model.transactionId = transactionId;
            return this;
        }

        public Builder withDividend(double dividend) {
            model.dividend = dividend;
            return this;
        }
    }
}
