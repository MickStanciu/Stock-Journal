package com.example.gateway.api.rest.configuration

import com.example.gateway.api.rest.filter.TokenFilter
import com.google.protobuf.ExtensionRegistry
import com.google.protobuf.util.JsonFormat
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter
import org.springframework.web.client.RestTemplate
import javax.servlet.DispatcherType

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

    @Bean
    fun registerTokenFilter(): FilterRegistrationBean<TokenFilter> {
        val filterRegistration = FilterRegistrationBean<TokenFilter>()
        filterRegistration.filter = TokenFilter()
        filterRegistration.urlPatterns = listOf("/api/v1/tradelog/*", "/api/v1/stockdata/*")
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST)
        filterRegistration.order = Ordered.LOWEST_PRECEDENCE - 1
        return filterRegistration
    }
}