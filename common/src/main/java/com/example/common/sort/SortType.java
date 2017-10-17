package com.example.common.sort;

public enum SortType {

    PRICE_ASC("price ASC"),
    PRICE_DESC("price DESC"),
    MODEL_ASC("model ASC"),
    MODEL_DESC("model DESC");

    private String name;

    SortType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    static public SortType lookup(String id, SortType defaultValue) {
        try {
            return SortType.valueOf(id);
        } catch (IllegalArgumentException ex) {
            return defaultValue;
        }
    }
}
