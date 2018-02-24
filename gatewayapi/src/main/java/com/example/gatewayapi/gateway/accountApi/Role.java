package com.example.gatewayapi.gateway.accountApi;

public class Role {

    private Integer id;
    private String name;
    private String description;

    public Role() {
        //required by jackson
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
