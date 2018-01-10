package com.example.catalog.rest;

import com.example.catalog.model.ItemDto;
import com.example.common.rest.dto.PaginationDto;
import com.example.common.rest.envelope.PageEnvelope;
import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.catalog.service.CatalogService;
import com.example.common.sort.SortType;
import org.apache.log4j.Logger;

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

    private static final Logger log = Logger.getLogger(CatalogRest.class);
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

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<ItemDto>()
                .withData(itemDto)
                .withErrors(Collections.emptyList())
                .build();

        return Response.status(Response.Status.OK)
                .entity(responseEnvelope)
                .build();
    }

    @GET
    public Response findAll(
            @QueryParam("limit") @DefaultValue(MAX_ITEMS_PER_PAGE) int limit,
            @QueryParam("offset") @DefaultValue("0") int offset,
            @QueryParam("sort") @DefaultValue("MODEL_ASC") String sort
            ) {

        if (limit < 0 || offset < 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        SortType sortType = SortType.lookup(sort.toUpperCase(), SortType.MODEL_ASC);

        List<BigInteger> paginatedIds = catalogService.getIdList(limit, offset, sortType);
        List<ItemDto> itemList = catalogService.getItemList(paginatedIds, sortType);


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
