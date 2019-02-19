package com.example.tradelog.api.spec.model;

import java.io.Serializable;

public class HealthModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean firstRecordOk;

    public boolean isFirstRecordOk() {
        return firstRecordOk;
    }

    public void setFirstRecordOk(boolean firstRecordOk) {
        this.firstRecordOk = firstRecordOk;
    }
}
