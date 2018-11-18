package com.example.tenant.api.spec;

import java.io.Serializable;

public class TenantModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    public TenantModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private TenantModel() {
        //required by Jackson
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
