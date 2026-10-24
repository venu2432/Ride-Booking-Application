package com.project.ridebooking.RideBookingApplication.Security;

import com.project.ridebooking.RideBookingApplication.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JWTService {

    private static final String TOKEN_TYPE_CLAIM = "tokenType";
    private static final String ACCESS = "access";
    private static final String REFRESH = "refresh";

    @Value("${jwt.secretkey}")
    private String secretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("roles", user.getRoles().toString())
                .claim(TOKEN_TYPE_CLAIM, ACCESS)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*10))
                .signWith(getSecretKey())
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim(TOKEN_TYPE_CLAIM, REFRESH)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L *60*60*24*30*6))
                .signWith(getSecretKey())
                .compact();
    }

    public Long getUserIdFromAccessToken(String token) {
        return getUserIdFromToken(token, ACCESS);
    }

    public Long getUserIdFromRefreshToken(String token) {
        return getUserIdFromToken(token, REFRESH);
    }

    private Long getUserIdFromToken(String token, String expectedType) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        if (!expectedType.equals(claims.get(TOKEN_TYPE_CLAIM, String.class))) {
            throw new JwtException("Invalid token type");
        }
        return Long.valueOf(claims.getSubject());
    }
}
