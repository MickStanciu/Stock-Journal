package com.example.shop.service;

import com.example.common.sort.SortType;
import com.example.shop.gateway.AvailabilityGateway;
import com.example.shop.gateway.CatalogGateway;
import com.example.shop.model.PaginatedCatalogItemResponse;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ProductService {

    @Inject
    private CatalogGateway catalogGateway;

    @Inject
    private AvailabilityGateway availabilityGateway;

    public PaginatedCatalogItemResponse getPaginatedItems(int limit, int offset, SortType sortType) {
        return catalogGateway.getPaginatedItems(limit, offset, sortType);
    }
}
