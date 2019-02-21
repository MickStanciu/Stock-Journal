package com.example.gateway.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;

public class TokenUtil {

    private static final Logger log = LoggerFactory.getLogger(TokenUtil.class);
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    private static final String SIGNATURE = "e8SZbGZiw59dw7E4IXDDuA==";
    public static final String ISSUER = "Bendis";
    public static final long TTL = 1209600000L; //14 days

    public static String generateToken(String accountId, Integer roleId) {

        long nowMillis = System.currentTimeMillis();

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject("auth")
                .claim("accountId", accountId)
                .claim("roleId", roleId)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(nowMillis + TTL))
                .signWith(ALGORITHM, TextCodec.BASE64.decode(SIGNATURE))
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);
            return ISSUER.equals(claims.getIssuer());
        } catch (Exception ex) {
            log.error("Token validation error! " + ex.getMessage());
            return false;
        }
    }

    public static Optional<TokenClaims> getTokenClaims(String token) {
        try {
            Claims claims = getClaims(token);
            TokenClaims tokenClaims = new TokenClaims();
            tokenClaims.setTenantId((String) claims.get("tenantId"));
            Number tmp = (Number) claims.get("accountId");
            tokenClaims.setAccountId(BigInteger.valueOf(tmp.longValue()));
            tokenClaims.setRoleId((Integer) claims.get("roleId"));
            tokenClaims.setIssuer(claims.getIssuer());
            return Optional.of(tokenClaims);
        } catch (Exception ex) {
            log.error("Token validation error! " + ex.getMessage());
            return Optional.empty();
        }
    }

    public static Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNATURE)
                .parseClaimsJws(token)
                .getBody();
    }

}
