package com.example.gateway.api.rest.controller

import com.example.gateway.api.core.service.StockDataService
import com.example.gateway.api.rest.converter.ShareDataConverter
import com.example.gateway.api.rest.converter.TokenConverter
import com.example.gateway.api.spec.model.GWShareDataDto
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/*
    UTILITY CONTROLLER. SHOULD NOT BE EXPOSED
 */
@RestController
@RequestMapping(value = ["/api/v1/util"], produces = [UtilController.PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class UtilController(private val stockDataService: StockDataService) {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    @RequestMapping(value = ["/price/{symbol}", "/price/{symbol}/"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun updatePriceForSymbol(
            @PathVariable(name = "symbol") symbol: String,
            @RequestBody dto: GWShareDataDto
    ) {
        val model = ShareDataConverter.toModel(dto)
        stockDataService.updatePrice(model)
    }

    @RequestMapping(value = ["/token/{accountId}", "/token/{accountId}/"], method = [RequestMethod.GET])
    fun createToken(@PathVariable("accountId") accountId: String) :String {
        return TokenConverter.encode(accountId)
    }
}
