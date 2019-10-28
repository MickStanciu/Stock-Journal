package com.example.tradelog.api.configuration;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.util.JsonFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class RestConfig {

//    @Bean
//    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
//        return new ProtobufHttpMessageConverter();
//    }

    @Bean
    ProtobufJsonFormatHttpMessageConverter protobufJsonFormatHttpMessageConverter() {
        JsonFormat.Printer printer = JsonFormat.printer().omittingInsignificantWhitespace();
        JsonFormat.Parser parser = JsonFormat.parser().ignoringUnknownFields();
        return new ProtobufJsonFormatHttpMessageConverter(parser, printer, (ExtensionRegistry) null);
    }

//    @Bean
//    RestTemplate restTemplate(ProtobufHttpMessageConverter httpMessageConverter) {
//        return new RestTemplate(Arrays.asList(httpMessageConverter));
//    }
}
