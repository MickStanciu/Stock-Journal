package com.example.gatewayapi.security;

import com.example.common.security.TokenUtil;
import io.jsonwebtoken.Claims;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class TokenUtilTest {
    private static String tenantId = "testTenant";
    private static String accountName = "Rick and Morty";
    private static String roleName = "admin";
    private static String token = null;
    private static Claims claims;


    @BeforeClass
    public static void setup() {
        token = TokenUtil.generateToken(tenantId, accountName, roleName);
        assertNotEquals(token, "");
        claims = TokenUtil.getClaims(token);
    }

    @Test
    void testTokenIssuer() {
        assertEquals(TokenUtil.ISSUER, claims.getIssuer());
    }

    @Test
    void testTokenExpTime() {
        assertEquals(TokenUtil.TTL, claims.getExpiration().getTime() - claims.getIssuedAt().getTime());
    }

    @Test
    void testTokenTenant() {
        assertEquals(tenantId, claims.get("tenantId"));
    }

    @Test
    void testTokenRole() {
        assertEquals(roleName, claims.get("roleName"));
    }

    @Test
    void testTokenName() {
        assertEquals(accountName, claims.get("accountName"));
    }

    @Test
    void testValidateTruthy() {
        assertTrue(TokenUtil.validateToken(token));
    }

    @Test
    void testValidateFalsy() {
        assertFalse(TokenUtil.validateToken("x"));
        assertFalse(TokenUtil.validateToken(null));
    }
}
