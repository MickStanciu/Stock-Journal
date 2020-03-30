package com.example.account.api.core.service

import java.math.BigInteger
import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


class HashUtils {
    companion object {

        private const val iterations = 65536
        private const val keyLen = 512

        fun hashPassword(clearTextPassword: String): String? {
            val salt = generateSalt() ?: return null

            val spec: KeySpec = PBEKeySpec(clearTextPassword.toCharArray(), salt, iterations, keyLen)
            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val hash = factory.generateSecret(spec).encoded
            return toHex(salt) + ":" + toHex(hash)
        }

        fun validatePassword(clearTextPassword: String, hashedPassword: String): Boolean {
            val parts = hashedPassword.split(":")
            val inputSalt = fromHex(parts[0])
            val inputHash = fromHex(parts[1])

            val spec: KeySpec = PBEKeySpec(clearTextPassword.toCharArray(), inputSalt, iterations, keyLen)
            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val generatedHash = factory.generateSecret(spec).encoded

            if (inputHash.size != generatedHash.size) {
                return false
            }

            for (i in inputHash.indices) {
                if (inputHash[i] != generatedHash[i]) {
                    return false
                }
            }

            return true
        }

        private fun generateSalt(): ByteArray? {
            val sr = SecureRandom.getInstance("SHA1PRNG")
            val salt = ByteArray(16)
            sr.nextBytes(salt)
            return salt
        }

        fun toHex(array: ByteArray): String? {
            val bi = BigInteger(1, array)
            val hex = bi.toString(16)
            val paddingLength = array.size * 2 - hex.length
            return if (paddingLength > 0) {
                String.format("%0" + paddingLength + "d", 0) + hex
            } else {
                hex
            }
        }

        private fun fromHex(hex: String): ByteArray {
            val bytes = ByteArray(hex.length / 2)
            for (i in bytes.indices) {
                bytes[i] = hex.substring(2 * i, 2 * i + 2).toInt(16).toByte()
            }
            return bytes

        }
    }
}