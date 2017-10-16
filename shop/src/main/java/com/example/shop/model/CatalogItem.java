package com.example.shop.model;

import java.math.BigDecimal;

public class CatalogItem {

    private int id;
    private String model;
    private String sku;
    private BigDecimal price;

    public CatalogItem(int id, String model, String sku, BigDecimal price) {
        this.id = id;
        this.model = model;
        this.sku = sku;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getSku() {
        return sku;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
