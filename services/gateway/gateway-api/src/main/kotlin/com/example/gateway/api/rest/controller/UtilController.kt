package com.example.gateway.api.rest.controller

import com.example.gateway.api.core.service.StockDataService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/v1/util"], produces = [UtilController.PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class UtilController(private val stockDataService: StockDataService) {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    @RequestMapping(value = ["/price/{symbol}", "/price/{symbol}/"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun updatePriceForSymbol(
            @RequestHeader("accountId") accountId: String,
            @PathVariable(name = "symbol") symbol: String) {

        //todo: validate input

        stockDataService.updatePrice(symbol)
    }
}