package com.example.gateway.api.model;

import java.io.Serializable;

public class TransactionSettingsGWModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private boolean groupSelected;
    private boolean legClosed;

    public TransactionSettingsGWModel() {
        //required by Jackson
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isGroupSelected() {
        return groupSelected;
    }

    public void setGroupSelected(boolean groupSelected) {
        this.groupSelected = groupSelected;
    }

    public boolean isLegClosed() {
        return legClosed;
    }

    public void setLegClosed(boolean legClosed) {
        this.legClosed = legClosed;
    }
}
