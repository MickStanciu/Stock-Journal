package com.example.shop.catalog.model;

import java.io.Serializable;

public class Attribute implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String value;

    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
