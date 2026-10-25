package com.project.ridebooking.RideBookingApplication.Security;

import com.project.ridebooking.RideBookingApplication.Entity.Enums.Role;
import com.project.ridebooking.RideBookingApplication.Entity.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JWTServiceTest {

    private static final String SECRET = "0123456789abcdef0123456789abcdef";
    private final JWTService jwtService = new JWTService();
    private User user;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtService, "secretKey", SECRET);
        user = mock(User.class);
        when(user.getId()).thenReturn(42L);
        when(user.getRoles()).thenReturn(Set.of(Role.RIDER));
    }

    @Test
    void accessTokenIsNotAcceptedForRefresh() {
        String accessToken = jwtService.generateToken(user);

        assertEquals(Long.valueOf(42L), jwtService.getUserIdFromAccessToken(accessToken));
        assertThrows(JwtException.class, () -> jwtService.getUserIdFromRefreshToken(accessToken));
    }

    @Test
    void refreshTokenIsNotAcceptedForAccess() {
        String refreshToken = jwtService.generateRefreshToken(user);

        assertEquals(Long.valueOf(42L), jwtService.getUserIdFromRefreshToken(refreshToken));
        assertThrows(JwtException.class, () -> jwtService.getUserIdFromAccessToken(refreshToken));
    }

    @Test
    void tokensWithoutPurposeAreRejected() {
        String legacyToken = Jwts.builder()
                .subject("42")
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                .compact();

        assertThrows(JwtException.class, () -> jwtService.getUserIdFromAccessToken(legacyToken));
        assertThrows(JwtException.class, () -> jwtService.getUserIdFromRefreshToken(legacyToken));
    }
}
