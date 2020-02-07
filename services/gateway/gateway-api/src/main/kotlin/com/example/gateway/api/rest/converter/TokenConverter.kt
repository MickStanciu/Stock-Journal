package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.TokenClaims
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.TextCodec
import org.slf4j.LoggerFactory
import java.util.*

class TokenConverter {

    companion object {

        private val LOG = LoggerFactory.getLogger(TokenConverter::class.java)
        private val ALGORITHM = SignatureAlgorithm.HS256
        private const val SIGNATURE = "e8SZbGZiw59dw7E4IXDDuA=="
        private const val ISSUER = "Bendis"
        private const val TTL = 1209600000L //14 days


        fun decode(token: String): TokenClaims? {
            try {
                val claims = getClaims(token)
                return TokenClaims(tenantId = claims["tenantId"] as String,
                        issuer = claims.issuer,
                        accountId = claims["accountId"] as Int,
                        roleId = claims["roleId"] as Int)
            } catch (ex: Exception) {
                LOG.error("Token validation error!", ex.message)
                return null
            }
        }

        fun encode(accountId: String): String {
            val nowMillis = System.currentTimeMillis()

            return Jwts.builder()
                    .setIssuer(ISSUER)
                    .setSubject("auth")
                    .claim("roleId", 1)
                    .claim("accountId", accountId)
                    .setIssuedAt(Date(nowMillis))
                    .setExpiration(Date(nowMillis + TTL))
                    .signWith(ALGORITHM, TextCodec.BASE64.decode(SIGNATURE))
                    .compact()
        }

        private fun getClaims(token: String): Claims {
            return Jwts.parser()
                    .setSigningKey(SIGNATURE)
                    .parseClaimsJws(token)
                    .body
        }
    }
}
