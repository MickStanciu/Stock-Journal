package com.example.rest;

import com.example.model.ItemDto;
import com.example.model.PaginationDto;
import com.example.model.envelope.PageEnvelope;
import com.example.model.envelope.ResponseEnvelope;
import com.example.service.CatalogService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Stateless
@Path("/")
@Produces("application/json")
public class CatalogRest {

    private static final String MAX_ITEMS_PER_PAGE = "50";


    @Inject
    private CatalogService catalogService;

    @GET
    @Path("/{id}")
    public Response find(@PathParam("id") int id) {

        if (id < 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        ItemDto itemDto = catalogService.getItem(id);
        if (itemDto == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.ResponseEnvelopeBuilder<ItemDto>()
                .data(catalogService.getItem(id))
                .errors(Collections.emptyList())
                .build();

        return Response.status(Response.Status.OK)
                .entity(responseEnvelope)
                .build();
    }

    @GET
    public Response findAll(
            @QueryParam("limit") @DefaultValue(MAX_ITEMS_PER_PAGE) int limit,
            @QueryParam("offset") @DefaultValue("0") int offset
            ) {

        if (limit < 0 || offset < 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<BigInteger> paginatedIds = catalogService.getIdList(limit, offset);
        List<ItemDto> itemList = catalogService.getItemList(paginatedIds);


        PageEnvelope pageEnvelope = new PageEnvelope.PageEnvelopeBuilder<List<ItemDto>>()
                .data(itemList)
                .errors(Collections.emptyList())
                .pagination(new PaginationDto(offset, limit, paginatedIds.size()))
                .build();

        return Response
                .status(Response.Status.OK)
                .entity(pageEnvelope).build();
    }

}
