package com.example.gateway.api.rest.gateway

import com.example.account.api.spec.model.AAccountDto
import com.example.gateway.api.core.model.AccountModel
import com.example.gateway.api.rest.converter.AccountConverter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class AccountGateway(private val restTemplate: RestTemplate,
                     @Value("\${gateway.account.url}") private val url: String) {

    companion object {
        private val LOG = LoggerFactory.getLogger(AccountGateway::class.java)
        private const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    fun getAccount(username: String, password: String): AccountModel? {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("username", username)
                .queryParam("password", password)

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)

        var responseDto: AAccountDto? = null
        try {
            val responseEntity = restTemplate.exchange(builder.build("").toString(), HttpMethod.GET, HttpEntity<Any>(headers), AAccountDto::class.java)
            responseDto = responseEntity.body
        } catch (ex: Exception) {
            LOG.error("Cannot get account")
        }
        return if (responseDto != null) {
            AccountConverter.toModel(responseDto)
        } else {
            null
        }
    }
}