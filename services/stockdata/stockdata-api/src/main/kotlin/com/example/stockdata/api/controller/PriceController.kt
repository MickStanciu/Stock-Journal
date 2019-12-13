package com.example.stockdata.api.controller

import com.example.stockdata.api.converter.PriceConverter
import com.example.stockdata.api.exception.ExceptionCode
import com.example.stockdata.api.exception.PriceException
import com.example.stockdata.api.service.PriceService
import com.example.stockdata.api.spec.model.PriceResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/v1/price"])
class PriceController(val priceService: PriceService) {

    @RequestMapping(value = ["/last-close/{symbol}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getPriceForSymbol(
            @RequestHeader("accountId") accountId: String,
            @PathVariable symbol: String) : PriceResponse {

        //TODO: missing Request Validation
        if (symbol == "ABC") {
            throw PriceException(ExceptionCode.BAD_REQUEST)
        }

        val sharedPriceModel =  priceService.getPrice(symbol)
        val priceItemResponse = PriceConverter.toPriceItemResponse(sharedPriceModel)

        return PriceResponse.newBuilder()
                .addPrice(priceItemResponse)
                .build()
    }

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }
}
