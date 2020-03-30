package com.example.gateway.api.core.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.TextCodec
import java.util.*

class TokenUtil {

    companion object {
        private val ALGORITHM = SignatureAlgorithm.HS256
        private const val SIGNATURE = "e8SZbGZiw59dw7E4IXDDuA=="
        private const val ISSUER = "JadeBaboon"
//        private const val TTL = 1209600000L //14 days
        private const val TTL = 86400 //1 day

        fun generateToken(accountId: String):String {
            val nowMillis = System.currentTimeMillis()

            return Jwts.builder()
                    .setIssuer(ISSUER)
                    .setSubject("auth")
                    .claim("accountId", accountId)
                    .setIssuedAt(Date(nowMillis))
                    .setExpiration(Date(nowMillis + TTL))
                    .signWith(ALGORITHM, TextCodec.BASE64.decode(SIGNATURE))
                    .compact()
        }
    }
}