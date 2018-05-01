package com.example.account.model;

import java.io.Serializable;
import java.util.HashSet;
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

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Role role) {
        return new Builder(role);
    }

    public static class Builder {
        protected Role role;

        public Builder() {
            role = new Role();
        }

        public Builder(Role role) {
            this.role = role;
        }

        public Role build() {
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

        public Builder withPermissions(Set<RoleInfo> permissions) {
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

    public Set<RoleInfo> getPermissions() {
        return permissions;
    }
}
