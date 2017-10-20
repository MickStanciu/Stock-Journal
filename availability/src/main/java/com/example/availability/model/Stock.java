package com.example.availability.model;

import java.io.Serializable;

public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    private int itemFk;
    private int quantity;

    public Stock(int itemFk, int quantity) {
        this.itemFk = itemFk;
        this.quantity = quantity;
    }

    public int getItemFk() {
        return itemFk;
    }

    public int getQuantity() {
        return quantity;
    }
}
