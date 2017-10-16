package com.example.shop.model;

import java.math.BigDecimal;
import java.util.Set;

public class CatalogItem {

    private int id;
    private String model;
    private String sku;
    private BigDecimal price;
    private Set<CatalogItemAttribute> attributes;

    public CatalogItem() {
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

    public Set<CatalogItemAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<CatalogItemAttribute> attributes) {
        this.attributes = attributes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
