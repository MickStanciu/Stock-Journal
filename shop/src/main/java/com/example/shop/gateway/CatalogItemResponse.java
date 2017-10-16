package com.example.shop.gateway;


import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.shop.model.CatalogItem;

public class CatalogItemResponse extends ResponseEnvelope<CatalogItem> {

    public CatalogItemResponse() {
    }

    public CatalogItemResponse(ResponseEnvelopeBuilder<CatalogItem> builder) {
        super(builder);
    }
}
