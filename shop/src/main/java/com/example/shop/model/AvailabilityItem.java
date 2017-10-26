package com.example.shop.model;

public class AvailabilityItem {

    private int itemFk;
    private int quantity;

    public AvailabilityItem() {
    }

    public int getItemFk() {
        return itemFk;
    }

    public void setItemFk(int itemFk) {
        this.itemFk = itemFk;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
