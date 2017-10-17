package com.example.shop.beans;

import com.example.common.sort.SortType;
import com.example.shop.gateway.CatalogGateway;
import com.example.shop.model.CatalogItem;
import com.example.shop.model.PaginatedCatalogItemResponse;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("homePageBean")
@RequestScoped
public class HomePageBean {

    @Inject
    private CatalogGateway catalogGateway;

    private List<CatalogItem> items;

    @PostConstruct
    public void initialize() {
        PaginatedCatalogItemResponse response = catalogGateway.getPaginatedItems(10,0, SortType.PRICE_DESC);
        items = response.getData();
    }

    public List<CatalogItem> getItems() {
        return items;
    }
}
