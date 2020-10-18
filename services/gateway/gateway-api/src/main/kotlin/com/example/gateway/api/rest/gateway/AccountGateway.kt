package com.example.gateway.api.rest.gateway

import arrow.core.Either
import com.example.account.api.spec.model.AAccountDto
import com.example.common.exception.ApiException
import com.example.common.exception.ApiExceptionCode
import com.example.gateway.api.core.model.AccountModel
import com.example.gateway.api.rest.converter.AccountConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class AccountGateway(private val restTemplate: RestTemplate,
                     @Value("\${gateway.account.url}") private val url: String) {

    companion object {
        private const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    fun getAccount(username: String, password: String): Either<ApiException, AccountModel> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("username", username)
                .queryParam("password", password)

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)

        val responseDto: AAccountDto?
        return try {
            val responseEntity = restTemplate.exchange(builder.build("").toString(), HttpMethod.GET, HttpEntity<Any>(headers), AAccountDto::class.java)
            responseDto = responseEntity.body
            if (responseDto != null) {
                Either.Right(AccountConverter.toModel(responseDto))
            } else {
                Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_DATA_CONVERSION_ERROR))
            }
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to AccountApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }
    }
}