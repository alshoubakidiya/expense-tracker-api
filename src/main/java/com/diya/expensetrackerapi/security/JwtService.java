package com.diya.expensetrackerapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
    public String generateToken(String username) {
        return Jwts.builder() //This is the JSON token builder method
                .subject(username) //It takes the user provided username and checks which user object it belongs to
                .issuedAt(new Date()) //Adds a timestamp of when the token was generated
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60)) //Timestamp of the tokens expiry date for security reasons
                .signWith(getSigningKey()) //Signs the token with SECRET_KEY to validate
                .compact(); //Finalizes everything into the actual JSON token string
    }
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
                return claims.getSubject();
    }
}
