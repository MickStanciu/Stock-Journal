package com.example.tenant.model;

import java.io.Serializable;

public class Health implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean registryOn;

    public boolean isRegistryOn() {
        return registryOn;
    }

    public void setRegistryOn(boolean registryOn) {
        this.registryOn = registryOn;
    }
}
