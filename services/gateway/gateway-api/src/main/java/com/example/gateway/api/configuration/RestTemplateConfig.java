//package com.example.gateway.api.configuration;
//
//import com.google.protobuf.ExtensionRegistry;
//import com.google.protobuf.util.JsonFormat;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;
//import org.springframework.web.client.RestTemplate;
//
//@Configuration
//public class RestTemplateConfig {
//
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder.build();
//    }
//
//    @Bean
//    ProtobufJsonFormatHttpMessageConverter protobufJsonFormatHttpMessageConverter() {
//        JsonFormat.Printer printer = JsonFormat.printer().omittingInsignificantWhitespace();
//        JsonFormat.Parser parser = JsonFormat.parser().ignoringUnknownFields();
//        return new ProtobufJsonFormatHttpMessageConverter(parser, printer, (ExtensionRegistry) null);
//    }
//
//}
