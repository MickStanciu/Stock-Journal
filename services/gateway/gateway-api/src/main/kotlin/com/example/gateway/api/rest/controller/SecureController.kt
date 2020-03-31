package com.example.gateway.api.rest.controller

import com.example.gateway.api.core.model.SupportMetadata
import com.example.gateway.api.rest.converter.SupportMetadataConverter
import com.example.gateway.api.rest.converter.TokenConverter

interface SecureController {

    fun getSupportData(token: String): SupportMetadata {
        return SupportMetadataConverter.build(accountId = TokenConverter.decode(token))
    }
}