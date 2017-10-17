package com.example.common.sort;

public enum SortType {

    PRICE_ASC("price ASC"),
    PRICE_DESC("price DESC"),
    MODEL_ASC("model ASC"),
    MODEL_DESC("model DESC");

    private String sort;

    SortType(String sort) {
        this.sort = sort;
    }

    public String getSort() {
        return sort;
    }

    static public SortType lookup(String id, SortType defaultValue) {
        try {
            return SortType.valueOf(id);
        } catch (IllegalArgumentException ex) {
            return defaultValue;
        }
    }

    @Override
    public String toString() {
        return this.name();
    }
}
