package com.example.tradelog.api.spec.model;


public class ShareJournalModel {

    private static final long serialVersionUID = 1L;

    private TransactionModel transactionDetails;
    private double price;
    private int quantity;
    private Action action;
    private ActionType actionType;
    private double brokerFees;

    public ShareJournalModel() {
        //required by Jackson
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
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

    public TransactionModel getTransactionDetails() {
        return transactionDetails;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        ShareJournalModel model;

        public Builder() {
            model = new ShareJournalModel();
        }

        public ShareJournalModel build() {
            ShareJournalModel buildModel = this.model;
            this.model = new ShareJournalModel();
            return buildModel;
        }

        public Builder withTransactionModel(TransactionModel transactionDetails) {
            model.transactionDetails = transactionDetails;
            return this;
        }

        public Builder withPrice(double price) {
            model.price = price;
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

        public Builder withQuantity(int quantity) {
            model.quantity = quantity;
            return this;
        }
    }
}
