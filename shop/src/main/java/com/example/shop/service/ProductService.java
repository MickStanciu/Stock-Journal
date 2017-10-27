package com.example.shop.service;

import com.example.common.sort.SortType;
import com.example.shop.gateway.AvailabilityGateway;
import com.example.shop.gateway.CatalogGateway;
import com.example.shop.model.AvailabilityItem;
import com.example.shop.model.AvailabilityItemResponse;
import com.example.shop.model.CatalogItem;
import com.example.shop.model.PaginatedCatalogItemResponse;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class ProductService {

    @Inject
    private CatalogGateway catalogGateway;

    @Inject
    private AvailabilityGateway availabilityGateway;


    public PaginatedCatalogItemResponse getPaginatedItems(int limit, int offset, SortType sortType) {
        PaginatedCatalogItemResponse catalogResponse = catalogGateway.getPaginatedItems(limit, offset, sortType);
        List<CatalogItem> itemList = catalogResponse.getData();
        Map<Integer, AvailabilityItem> availabilityResponse = availabilityGateway.getBulkAvailability(getItemIds(itemList));

        for (CatalogItem item : itemList) {
            if (availabilityResponse.containsKey(item.getId())) {
                item.setQuantity(availabilityResponse.get(item.getId()).getQuantity());
            }
        }
        return catalogResponse;
    }

    private List<Integer> getItemIds(final List<CatalogItem> list) {
        List<CatalogItem> itemList = new ArrayList<>(list);
        itemList.addAll(list);

        return list.stream().map(CatalogItem::getId).collect(Collectors.toList());
    }
}
