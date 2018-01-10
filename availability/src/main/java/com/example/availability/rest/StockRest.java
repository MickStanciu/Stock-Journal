package com.example.availability.rest;

import com.example.availability.model.Stock;
import com.example.availability.service.StockService;
import com.example.common.rest.envelope.ResponseEnvelope;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Stateless
@Path("/")
@Produces("application/json")
public class StockRest {

    private static final Logger log = Logger.getLogger(StockRest.class);

    @Inject
    private StockService stockService;

    @GET
    @Path("/{id}")
    public Response find(@PathParam("id") int id) {

        if (id < 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Stock stockItem = stockService.getStock(id);
        if (stockItem == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<Stock>()
                .withData(stockItem)
                .withErrors(Collections.emptyList())
                .build();

        return Response.status(Response.Status.OK)
                .entity(responseEnvelope)
                .build();
    }

    @GET
    @Path("/ids/{params}")
    public Response findAll(@PathParam("params") String params) {
        List<Integer> idList;
        try {
            idList = Stream.of(params.split(",")).map(Integer::new).collect(Collectors.toList());
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Stock> stockList = stockService.getBulkStock(idList);
        if (stockList.size() == 0) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<List<Stock>>()
                .withData(stockList)
                .withErrors(Collections.emptyList())
                .build();

        return Response.status(Response.Status.OK)
                .entity(responseEnvelope)
                .build();
    }

}
