package com.example.account.model;

import java.io.Serializable;

public class Health implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean firstRecordOk;

    public boolean isFirstRecordOk() {
        return firstRecordOk;
    }

    public void setFirstRecordOk(boolean firstRecordOk) {
        this.firstRecordOk = firstRecordOk;
    }
}
