package com.example.account.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Set<RoleInfo> permissions;

    public Role() {
        //required by Jackson
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.permissions = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<RoleInfo> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<RoleInfo> permissions) {
        this.permissions = permissions;
    }
}
