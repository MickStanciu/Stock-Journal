package com.example.gatewayapi.security;

import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TokenUtilTest {
    private String tenantId = "testTenant";
    private String accountName = "Rick and Morty";
    private String roleName = "admin";
    private String token = null;
    private Claims claims;


    @Before
    public void setup() {
        token = TokenUtil.generateToken(tenantId, accountName, roleName);
        assertNotEquals(token, "");
        claims = TokenUtil.getClaims(token);
    }

    @Test
    public void testTokenIssuer() {
        assertEquals(TokenUtil.ISSUER, claims.getIssuer());
    }

    @Test
    public void testTokenExpTime() {
        assertEquals(TokenUtil.TTL, claims.getExpiration().getTime() - claims.getIssuedAt().getTime());
    }

    @Test
    public void testTokenTenant() {
        assertEquals(tenantId, claims.get("tenantId"));
    }

    @Test
    public void testTokenRole() {
        assertEquals(roleName, claims.get("roleName"));
    }

    @Test
    public void testTokenName() {
        assertEquals(accountName, claims.get("accountName"));
    }

    @Test
    public void testValidateTruthy() {
        assertTrue(TokenUtil.validateToken(token));
    }

    @Test
    public void testValidateFalsy() {
        assertFalse(TokenUtil.validateToken("x"));
        assertFalse(TokenUtil.validateToken(null));
    }
}
