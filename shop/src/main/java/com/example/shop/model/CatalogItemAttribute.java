package com.example.shop.model;

import java.io.Serializable;

public class CatalogItemAttribute implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String value;

    public CatalogItemAttribute(String name, String value) {
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
