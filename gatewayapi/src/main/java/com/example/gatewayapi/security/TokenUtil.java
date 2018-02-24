package com.example.gatewayapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.apache.log4j.Logger;

import java.util.Date;

public class TokenUtil {

    private static final Logger log = Logger.getLogger(TokenUtil.class);
    private static SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    private static String SIGNATURE = "e8SZbGZiw59dw7E4IXDDuA==";
    public static String ISSUER = "Bendis";
    public static long TTL = 3600000L;

    public static String generateToken(String tenantId, String accountName, String role) {

        long nowMillis = System.currentTimeMillis();

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject("auth")
                .claim("tenantId", tenantId)
                .claim("accountName", accountName)
                .claim("roleName", role)
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
            log.error("Token validation error!");
            return false;
        }
    }

    static Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNATURE)
                .parseClaimsJws(token)
                .getBody();
    }

}
