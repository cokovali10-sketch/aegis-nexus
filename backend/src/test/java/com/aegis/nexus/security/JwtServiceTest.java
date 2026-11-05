package com.aegis.nexus.security.jwt;

import com.aegis.nexus.security.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class JwtServiceTest {

    @Test
    void generateAndExtractToken() {
        JwtService jwtService = new JwtService("my-secret-key-that-is-at-least-32-chars-long", 86400000L);
        UserDetails userDetails = User.withUsername("analyst1").password("password").roles("ANALYST").build();

        String token = jwtService.generateToken(userDetails);
        assertThat(token).isNotEmpty();

        String extractedUsername = jwtService.extractUsername(token);
        assertThat(extractedUsername).isEqualTo("analyst1");

        boolean valid = jwtService.isTokenValid(token, userDetails);
        assertThat(valid).isTrue();
    }

    @Test
    void tokenExpirationWorks() {
        JwtService jwtService = new JwtService("my-secret-key-that-is-at-least-32-chars-long", 1000L);
        UserDetails userDetails = User.withUsername("analyst1").password("password").roles("ANALYST").build();

        String token = jwtService.generateToken(userDetails);
        assertThat(jwtService.isTokenValid(token, userDetails)).isTrue();

        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertThat(jwtService.isTokenValid(token, userDetails)).isFalse();
    }
}
