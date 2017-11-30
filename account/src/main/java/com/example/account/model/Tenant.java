package com.example.account.model;

import java.io.Serializable;

public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;

    public Tenant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
