package com.example.account.api

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.*

@SpringBootApplication
class AccountApi {

    @Bean
    fun localeResolver(): LocaleResolver? {
        val localeResolver = AcceptHeaderLocaleResolver()
        localeResolver.defaultLocale = Locale.US
        return localeResolver
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(AccountApi::class.java, *args)
}
