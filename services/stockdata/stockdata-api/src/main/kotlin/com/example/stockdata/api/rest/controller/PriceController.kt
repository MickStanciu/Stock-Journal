package com.example.stockdata.api.rest.controller

import com.example.stockdata.api.core.service.PriceService
import com.example.stockdata.api.rest.converter.PriceConverter
import com.example.stockdata.api.rest.exception.ExceptionCode
import com.example.stockdata.api.rest.exception.PriceException
import com.example.stockdata.api.rest.validator.RequestValidator
import com.example.stockdata.api.spec.model.LastUpdatePriceResponse
import com.example.stockdata.api.spec.model.PriceResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/v1/price"])
class PriceController(private val priceService: PriceService) {

    @RequestMapping(value = ["/last-close/{symbol}"], method = [RequestMethod.GET], produces = [PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getPriceForSymbol(
            @RequestHeader("accountId") accountId: String,
            @PathVariable symbol: String) : PriceResponse {

        if (!RequestValidator.validateGetPriceForSymbol(accountId, symbol)) {
            throw PriceException(ExceptionCode.BAD_REQUEST)
        }

        val sharedPriceModel = priceService.getPrice(symbol) ?: throw PriceException(ExceptionCode.NO_DATA)
        val priceItemResponse = PriceConverter.toPriceItemResponse(sharedPriceModel)

        return PriceResponse.newBuilder()
                .addPrice(priceItemResponse)
                .build()
    }

    /*
        Return symbols where prices where not updated for a while
     */
    @RequestMapping(value = ["/old"], method = [RequestMethod.GET], produces = [PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getOldestSymbols(
            @RequestHeader("accountId") accountId: String,
            @RequestParam(name = "limit") limit: Int): LastUpdatePriceResponse {

        if (!RequestValidator.validateGetOldestSymbols(accountId, limit)) {
            throw PriceException(ExceptionCode.BAD_REQUEST)
        }

        var adjustedLimit = limit
        if (adjustedLimit > 10) {
            adjustedLimit = 10
        }

        val symbols = priceService.getOldestPrices(adjustedLimit)

        return LastUpdatePriceResponse.newBuilder()
                .addAllSymbols(symbols)
                .build()
    }

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }
}