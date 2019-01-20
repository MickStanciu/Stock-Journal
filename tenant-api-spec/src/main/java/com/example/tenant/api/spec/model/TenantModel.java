package com.example.tenant.api.spec.model;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class TenantModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(min = 1, max = 255)
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
