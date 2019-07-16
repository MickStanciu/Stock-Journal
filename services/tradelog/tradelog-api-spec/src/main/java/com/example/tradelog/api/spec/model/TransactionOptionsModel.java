package com.example.tradelog.api.spec.model;

import java.io.Serializable;

public class TransactionOptionsModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean groupSelected;

    public boolean isGroupSelected() {
        return groupSelected;
    }

    public void setGroupSelected(boolean groupSelected) {
        this.groupSelected = groupSelected;
    }
}
