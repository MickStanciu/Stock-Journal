package com.example.gateway.api.rest.configuration

import com.google.protobuf.ExtensionRegistry
import com.google.protobuf.util.JsonFormat
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter
import org.springframework.web.client.RestTemplate

@Configuration
class RestConfiguration {
    @Bean
    fun protobufHttpMessageConverter(): ProtobufHttpMessageConverter = ProtobufHttpMessageConverter()

    @Bean
    fun protobufJsonFormatHttpMessageConverter(): ProtobufJsonFormatHttpMessageConverter? {
        val printer = JsonFormat.printer().omittingInsignificantWhitespace()
        val parser = JsonFormat.parser().ignoringUnknownFields()
        return ProtobufJsonFormatHttpMessageConverter(parser, printer, null as ExtensionRegistry?)
    }

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.build()
    }
}