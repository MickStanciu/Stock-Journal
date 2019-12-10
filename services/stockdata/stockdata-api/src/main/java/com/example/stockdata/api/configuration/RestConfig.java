package com.example.stockdata.api.configuration;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.util.JsonFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;

@Configuration
public class RestConfig {

    @Bean
    ProtobufJsonFormatHttpMessageConverter protobufJsonFormatHttpMessageConverter() {
        JsonFormat.Printer printer = JsonFormat.printer().omittingInsignificantWhitespace();
        JsonFormat.Parser parser = JsonFormat.parser().ignoringUnknownFields();
        return new ProtobufJsonFormatHttpMessageConverter(parser, printer, (ExtensionRegistry) null);
    }

}
