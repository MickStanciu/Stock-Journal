package com.example.tradelog.api.spec.model;

import java.io.Serializable;

public class TransactionSettingsModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionId;

    private Double preferredPrice;
    private boolean groupSelected;
    private boolean legClosed;

    public TransactionSettingsModel() {
        //required by Jackson
    }

    public String getTransactionId() {
        return transactionId;
    }

    public boolean isGroupSelected() {
        return groupSelected;
    }

    public boolean isLegClosed() {
        return legClosed;
    }

    public Double getPreferredPrice() {
        return preferredPrice;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        TransactionSettingsModel model;

        public Builder() {
            model = new TransactionSettingsModel();
        }

        public TransactionSettingsModel build() {
            TransactionSettingsModel buildModel = this.model;
            this.model = new TransactionSettingsModel();
            return buildModel;
        }

        public Builder withTransactionId(String transactionId) {
            model.transactionId = transactionId;
            return this;
        }

        public Builder withGroupSelected(boolean groupSelected) {
            model.groupSelected = groupSelected;
            return this;
        }

        public Builder withLegClosed(boolean legClosed) {
            model.legClosed = legClosed;
            return this;
        }

        public Builder withPreferredPrice(Double preferredPrice) {
            model.preferredPrice = preferredPrice;
            return this;
        }
    }

}
