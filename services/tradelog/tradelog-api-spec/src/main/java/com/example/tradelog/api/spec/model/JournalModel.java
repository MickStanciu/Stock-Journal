package com.example.tradelog.api.spec.model;

import java.io.Serializable;

public class JournalModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionId;

    public JournalModel() {
        //required by Jackson
    }

    public String getTransactionId() {
        return transactionId;
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
    }
}
