package com.example.catalog.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

public class ItemDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String model;
    private String sku;
    private BigDecimal price;
    private Set<Attribute> attributes;

    public ItemDto(int id, String model, String sku, BigDecimal price) {
        this.id = id;
        this.model = model;
        this.sku = sku;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }
}
