package com.example.shop.beans;

import com.example.shop.gateway.CatalogGateway;
import com.example.shop.model.CatalogItem;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;

@Named("productDetailBean")
@RequestScoped
public class ProductDetailBean {

    @Inject
    private CatalogGateway catalogGateway;

    private CatalogItem item;

    @PostConstruct
    public void initialize() {
        item = catalogGateway.getCatalogItem(2);
    }

    public void setCatalogGateway(CatalogGateway catalogGateway) {
        this.catalogGateway = catalogGateway;
    }

    public CatalogItem getItem() {
        return item;
    }
}
