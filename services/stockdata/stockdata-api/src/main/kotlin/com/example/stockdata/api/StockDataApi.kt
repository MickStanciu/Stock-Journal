package com.example.stockdata.api

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter

@SpringBootApplication
class StockDataApi {

}

fun main(args: Array<String>) {
    SpringApplication.run(StockDataApi::class.java, *args)
}
