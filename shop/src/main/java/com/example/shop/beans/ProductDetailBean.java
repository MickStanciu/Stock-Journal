package com.example.shop.beans;

import com.example.shop.gateway.CatalogGateway;
import com.example.shop.model.CatalogItem;
import com.example.shop.model.CatalogItemResponse;

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
        CatalogItemResponse response = catalogGateway.getCatalogItem(2);
        item = response.getData();
    }

    public CatalogItem getItem() {
        return item;
    }
}
