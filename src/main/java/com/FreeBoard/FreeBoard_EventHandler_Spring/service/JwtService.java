package com.FreeBoard.FreeBoard_EventHandler_Spring.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Извлечение конкретного claim из JWT
    public Claims extractClaim(String token) {
        final Claims claims = extractAllClaims(token);
        return claims;
    }

    public boolean isExpired(Date expiration) {
        try {
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}