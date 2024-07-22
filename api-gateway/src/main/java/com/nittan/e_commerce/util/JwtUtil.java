package com.nittan.e_commerce.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

/**
 * Utility class for JWT token generation and validation.
 */
@Component
public class JwtUtil {

    // Secret key for JWT token signing
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    /**
     * Validates a JWT token.
     * @param token The JWT token to validate
     */
    public void validateToken(final String token){
        System.out.println("validate token called in gateway filter");
        Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
    }

    /**
     * Retrieves the signing key from base64-encoded secret.
     * @return Signing key
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
