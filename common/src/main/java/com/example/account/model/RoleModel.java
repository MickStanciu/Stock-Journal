package com.example.account.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class RoleModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Set<RoleInfoModel> permissions;

    public RoleModel() {
        //required by Jackson
    }

    public RoleModel(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.permissions = new HashSet<>();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(RoleModel role) {
        return new Builder(role);
    }

    public static class Builder {
        protected RoleModel role;

        public Builder() {
            role = new RoleModel();
        }

        public Builder(RoleModel role) {
            this.role = role;
        }

        public RoleModel build() {
            if (role.permissions == null) {
                role.permissions = new HashSet<>();
            }
            return role;
        }

        public Builder withId(Integer id) {
            role.id = id;
            return this;
        }

        public Builder withName(String name) {
            role.name = name;
            return this;
        }

        public Builder withPermissions(Set<RoleInfoModel> permissions) {
            role.permissions = permissions;
            return this;
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<RoleInfoModel> getPermissions() {
        return permissions;
    }
}