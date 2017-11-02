package com.example.catalog.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

public class ItemDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String model;
    private String sku;
    private BigDecimal price;
    private Currency currency;
    private Set<Attribute> attributes;

    public ItemDto(int id, String model, String sku, BigDecimal price, Currency currency) {
        this.id = id;
        this.model = model;
        this.sku = sku;
        this.price = price;
        this.currency = currency;
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

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Currency getCurrency() {
        return currency;
    }
}
