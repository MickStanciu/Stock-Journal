package com.example.tradelog.api.spec.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class TransactionModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String accountId;
    private OffsetDateTime date;
    private String symbol;
    private TransactionType type;
    private TransactionSettingsModel settings;

    public TransactionModel() {
        //required by Jackson
    }

    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public String getSymbol() {
        return symbol;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionSettingsModel getSettings() {
        return settings;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        TransactionModel model;

        public Builder() {
            model = new TransactionModel();
        }

        public TransactionModel build() {
            TransactionModel buildModel = this.model;
            this.model = new TransactionModel();
            return buildModel;
        }

        public Builder withId(String id) {
            model.id = id;
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

        public Builder withSymbol(String symbol) {
            model.symbol = symbol;
            return this;
        }

        public Builder withType(TransactionType type) {
            model.type = type;
            return this;
        }

        public Builder withSettings(TransactionSettingsModel options) {
            model.settings = options;
            return this;
        }
    }
}
