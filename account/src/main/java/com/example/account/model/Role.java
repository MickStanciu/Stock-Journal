package com.example.account.model;

import java.io.Serializable;
import java.math.BigInteger;

public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;

    public Role() {
        //required by Jackson
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
