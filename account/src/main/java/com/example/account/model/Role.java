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

    public Role() {
        //required by Jackson
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

//    public static class Builder {
//        protected Role role;
//
//        public Builder() {
//            role = new Role();
//        }
//
//        public Builder(Role role) {
//            this.role = role;
//        }
//
//        public Role build() {
//            return role;
//        }
//
//        public Builder withId(Integer id) {
//            role.id = id;
//            return this;
//        }
//
//        public Builder withName(String name) {
//            role.name = name;
//            return this;
//        }
//    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
