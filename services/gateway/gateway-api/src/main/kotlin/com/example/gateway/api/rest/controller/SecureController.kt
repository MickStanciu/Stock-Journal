package com.example.gateway.api.rest.controller

import com.example.gateway.api.core.model.SupportMetadata
import com.example.gateway.api.rest.converter.SupportMetadataConverter
import com.example.gateway.api.rest.converter.TokenConverter

interface SecureController {

    fun getSupportData(token: String): SupportMetadata {
        val claims = TokenConverter.decode(token)
        return SupportMetadataConverter.build(accountId = claims.accountId)
    }
}