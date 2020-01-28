package com.example.gateway.api.rest.gateway

import com.example.gateway.api.core.model.SharePriceModel
import com.example.gateway.api.rest.converter.AlphaVantageCsvConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class AlphaVantageGateway(private val restTemplate: RestTemplate,
                          @Value("\${gateway.alphavantage.url}") private val API_URL: String,
                          @Value("\${gateway.alphavantage.key}") private val API_KEY: String) {

    private final val URL_TEMPLATE = "/query"

    fun getQuoteResponse(symbol: String): SharePriceModel? {
        val builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path(URL_TEMPLATE)
                .queryParam("function", "GLOBAL_QUOTE")
                .queryParam("symbol", symbol)
                .queryParam("interval", "5min")
                .queryParam("outputsize", "full")
                .queryParam("apikey", API_KEY)
                .queryParam("datatype", "csv")


        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        val responseEntity = restTemplate
                .exchange(builder.build("").toString(), HttpMethod.GET, HttpEntity<Any>(headers), String::class.java)

        val responseCSV: String? = responseEntity.body

        return if (responseCSV != null) {
            AlphaVantageCsvConverter.toModel(responseCSV)
        } else {
            null
        }
    }
}