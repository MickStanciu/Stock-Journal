package com.example.tradelog.api.spec.model;

import java.io.Serializable;

public class TransactionSettingsModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //todo: not sure if necessary
    private String transactionId;

    private boolean groupSelected;
    private boolean legClosed;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isGroupSelected() {
        return groupSelected;
    }

    public boolean isLegClosed() {
        return legClosed;
    }

    public void setGroupSelected(boolean groupSelected) {
        this.groupSelected = groupSelected;
    }

    public void setLegClosed(boolean legClosed) {
        this.legClosed = legClosed;
    }
}
