package com.example.tradelog.api.rest.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
open class JacksonConfig {

    @Autowired
    open fun objectMapper(builder: Jackson2ObjectMapperBuilder): ObjectMapper? {
        val objectMapper = builder.createXmlMapper(false).build<ObjectMapper>()
        objectMapper.registerModule(KotlinModule())
        return objectMapper
    }
}