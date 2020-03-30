package com.example.gateway.api.core.util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TokenUtilTest {

    @Test
    fun validateToken() {
        val token = TokenUtil.generateToken("d79ec11a-2011-4423-ba01-3af8de0a3e14")
        Assertions.assertNotNull(token)
        val isValid = TokenUtil.validateToken(token)
        Assertions.assertTrue(isValid)
    }

    @Test
    fun whenTokenIsExpired() {
        val token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKYWRlQmFib29uIiwic3ViIjoiYXV0aCIsImFjY291bnRJZCI6ImQ3OWVjMTFhLTIwMTEtNDQyMy1iYTAxLTNhZjhkZTBhM2UxNCIsImlhdCI6MTU4NTYxMTY5MiwiZXhwIjoxNTg1NjExNjA1fQ.cPIQnHwhIuX-li91yFqK7E-AHECqSd3TFQhkL-TKCRY"
        val isValid = TokenUtil.validateToken(token)
        Assertions.assertFalse(isValid)
    }

    @Test
    fun whenWrongIssuer() {
        val token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKYWRlQmFib29uMiIsInN1YiI6ImF1dGgiLCJhY2NvdW50SWQiOiJkNzllYzExYS0yMDExLTQ0MjMtYmEwMS0zYWY4ZGUwYTNlMTQiLCJpYXQiOjE1ODU2MTE5OTksImV4cCI6MTU4NTYxMjA4NX0.epmDKZUE5alPMhTbo5X5sjYNu5NNfAQnCydQqH2SiwE"
        val isValid = TokenUtil.validateToken(token)
        Assertions.assertFalse(isValid)
    }
}