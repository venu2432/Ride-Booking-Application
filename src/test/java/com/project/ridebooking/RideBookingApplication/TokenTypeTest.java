package com.project.ridebooking.RideBookingApplication;

import com.project.ridebooking.RideBookingApplication.Entity.User;
import com.project.ridebooking.RideBookingApplication.Repository.UserRepository;
import com.project.ridebooking.RideBookingApplication.Security.JWTService;
import com.project.ridebooking.RideBookingApplication.Security.JwtAuthFilter;
import com.project.ridebooking.RideBookingApplication.Service.Impl.AuthServiceImpl;
import com.project.ridebooking.RideBookingApplication.Service.UserService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenTypeTest {
    private static final String SECRET = "test-only-key-long-enough-for-hmac-sha256";

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AuthServiceImpl authService;

    private JWTService jwtService;
    private User user;

    @BeforeEach
    void setUp() {
        jwtService = new JWTService();
        ReflectionTestUtils.setField(jwtService, "secretKey", SECRET);
        ReflectionTestUtils.setField(authService, "jwtService", jwtService);
        user = new User();
        user.setId(42L);
        user.setEmail("rider@example.com");
        user.setRoles(Set.of());
    }

    @Test
    void onlyAccessTokensAreAcceptedForAuthentication() {
        String access = jwtService.generateToken(user);
        String refresh = jwtService.generateRefreshToken(user);

        assertEquals(42L, jwtService.getUserIdFromAccessToken(access));
        assertEquals(42L, jwtService.getUserIdFromRefreshToken(refresh));
        assertThrows(JwtException.class, () -> jwtService.getUserIdFromAccessToken(refresh));
        assertThrows(JwtException.class, () -> jwtService.getUserIdFromRefreshToken(access));
    }

    @Test
    void tokensWithoutTypeAreRejectedEvenIfSigned() {
        String legacyToken = Jwts.builder().subject("42")
                .expiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8))).compact();

        assertThrows(JwtException.class, () -> jwtService.getUserIdFromAccessToken(legacyToken));
        assertThrows(JwtException.class, () -> jwtService.getUserIdFromRefreshToken(legacyToken));
    }

    @Test
    void refreshRequiresRefreshTokenAndReturnsAccessToken() {
        when(userRepository.findById(42L)).thenReturn(Optional.of(user));
        String access = authService.refreshToken(jwtService.generateRefreshToken(user));

        assertEquals(42L, jwtService.getUserIdFromAccessToken(access));
        assertThrows(JwtException.class, () -> jwtService.getUserIdFromRefreshToken(access));
        assertThrows(JwtException.class, () -> authService.refreshToken(jwtService.generateToken(user)));
        verify(userRepository, times(1)).findById(42L);
    }

    @Test
    void refreshTokenInBearerHeaderNeverAuthenticates() throws Exception {
        UserService userService = mock(UserService.class);
        HandlerExceptionResolver resolver = mock(HandlerExceptionResolver.class);
        FilterChain chain = mock(FilterChain.class);
        JwtAuthFilter filter = new JwtAuthFilter(jwtService, userService);
        ReflectionTestUtils.setField(filter, "handlerExceptionResolver", resolver);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer " + jwtService.generateRefreshToken(user));

        filter.doFilter(request, response, chain);

        verify(resolver).resolveException(eq(request), eq(response), isNull(), isA(JwtException.class));
        verifyNoInteractions(userService, chain);
    }
}
