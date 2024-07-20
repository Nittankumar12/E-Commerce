package com.nittan.user_authentication_service.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    // predefined secret
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";// generating own secret using Secure Random Generator

    // validating token
    public void validateToken(final String token){
        Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token);
    }

    // generating token
    public String generateToken(String userName){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,userName);
    }

    // create token through claims and username
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // get the sign in key to sign the jwt token
    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}


//    private static final int secret_length = 64;
//    public static final String  SECRET = generateSecret();
//
//    private static String generateSecret() {
//        SecureRandom random = new SecureRandom();
//        byte[] bytes = new byte[secret_length];
//        random.nextBytes(bytes);
//        String secret =  Base64.getUrlEncoder().encodeToString(bytes);
//        System.out.println(secret);
//        return secret;
//    }
