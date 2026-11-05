package com.aegis.nexus.security;

import com.aegis.nexus.security.jwt.JwtService;
import com.aegis.nexus.security.model.Role;
import com.aegis.nexus.security.model.User;
import com.aegis.nexus.security.repository.UserRepository;
import com.aegis.nexus.security.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerNewUserSucceeds() {
        when(userRepository.existsByUsername("analyst1")).thenReturn(false);
        when(userRepository.existsByEmail("analyst1@corp.local")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("$2a$10$hashed");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId("user-1");
            return u;
        });

        User user = authService.register("analyst1", "analyst1@corp.local", "password123", "Corp", Role.ANALYST);

        assertThat(user.getUsername()).isEqualTo("analyst1");
        assertThat(user.getRole()).isEqualTo(Role.ANALYST);
        assertThat(user.isActive()).isTrue();
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerDuplicateUsernameThrows() {
        when(userRepository.existsByUsername("analyst1")).thenReturn(true);

        assertThat(org.assertj.core.api.Assertions.catchThrowable(() ->
            authService.register("analyst1", "analyst1@corp.local", "password123", "Corp", Role.ANALYST)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void extractUsernameFromToken() {
        when(jwtService.extractUsername("token-123")).thenReturn("analyst1");
        String username = authService.extractUsername("token-123");
        assertThat(username).isEqualTo("analyst1");
    }

    @Test
    void loadUserByUsernameFindsUser() {
        User user = new User();
        user.setUsername("analyst1");
        user.setPasswordHash("$2a$10$hashed");
        user.setRole(Role.ANALYST);
        user.setActive(true);

        when(userRepository.findByUsername("analyst1")).thenReturn(Optional.of(user));

        UserDetails details = authService.loadUserByUsername("analyst1");
        assertThat(details.getUsername()).isEqualTo("analyst1");
        assertThat(details.isEnabled()).isTrue();
    }

    @Test
    void loadUserByUsernameNotFoundThrows() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());
        assertThat(org.assertj.core.api.Assertions.catchThrowable(() ->
            authService.loadUserByUsername("unknown")))
            .isInstanceOf(org.springframework.security.core.userdetails.UsernameNotFoundException.class);
    }
}
