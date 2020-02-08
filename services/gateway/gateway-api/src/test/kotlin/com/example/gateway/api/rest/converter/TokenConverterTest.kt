package com.example.gateway.api.rest.converter

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TokenConverterTest {

    @Test
    fun testToken() {
        val accountId = "366b5bf8-2f37-4e17-862d-4613ee203e63"
        val token = TokenConverter.encode(accountId)
        Assertions.assertFalse(token.isBlank())

        val tokenClaims = TokenConverter.decode(token)
        Assertions.assertNotNull(tokenClaims)
        Assertions.assertEquals(accountId, tokenClaims.accountId)
    }

    @Test
    fun testValidation() {
        val accountId = "366b5bf8-2f37-4e17-862d-4613ee203e63"
        val token = TokenConverter.encode(accountId)
        Assertions.assertTrue(TokenConverter.validate(token))
    }
}