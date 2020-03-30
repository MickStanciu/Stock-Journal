package com.example.account.api.core.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HashUtilsTest {

    @Test
    fun testToHex() {
        val bytes = "ABCD".toByteArray()
        val hexed = HashUtils.toHex(bytes)
        Assertions.assertNotNull(hexed)
        Assertions.assertEquals("41424344", hexed)
    }

    @Test
    fun testHashPassword() {
        val password = "secret"
        val hash = HashUtils.hashPassword(password)
        Assertions.assertNotNull(hash)
    }

    @Test
    fun validatePassword() {
        val password = "secret"
        val storedHash = "08c755daa5cdde8338c792963c052e8e:9d2b6ca8a532cfeb9f74162caac221b9759d53ba2607e9aabff30666df09c6c3eb3a9c63a3866c9893a20ff580cc0793ded70d586d1360b0d7e52249a2d2beb6"
        val identical = HashUtils.validatePassword(password, storedHash)
        Assertions.assertTrue(identical)
    }
}