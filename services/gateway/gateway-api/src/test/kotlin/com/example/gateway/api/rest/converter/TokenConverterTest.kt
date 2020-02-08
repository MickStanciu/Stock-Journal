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

    @Test
    fun testExpiration() {
        val expToken = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJCZW5kaXMiLCJzdWIiOiJhdXRoIiwicm9sZUlkIjoxLCJhY2NvdW50SWQiOiIzNjZiNWJmOC0yZjM3LTRlMTctODYyZC00NjEzZWUyMDNlNjMiLCJpYXQiOjE1ODExNTAyNjcsImV4cCI6MTU3OTk0MDY2N30.rtqeK3nYmPulys7zLFXKfYeBmqRzruBkpi__13R4V-g"
        Assertions.assertFalse(TokenConverter.validate(expToken))
    }

    @Test
    fun testBadIssuer() {
        val badToken = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBQkNEIiwic3ViIjoiYXV0aCIsInJvbGVJZCI6MSwiYWNjb3VudElkIjoiMzY2YjViZjgtMmYzNy00ZTE3LTg2MmQtNDYxM2VlMjAzZTYzIiwiaWF0IjoxNTgxMTUwMDczLCJleHAiOjE1ODIzNTk2NzN9.5MTu16d9Ss1dez3X5QxzmmPDYyoHxEMUFdQtCnSH3jg"
        Assertions.assertFalse(TokenConverter.validate(badToken))
    }

}