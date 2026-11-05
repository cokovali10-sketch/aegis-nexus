package com.aegis.nexus.security.service;

import com.aegis.nexus.security.jwt.JwtService;
import com.aegis.nexus.security.model.Role;
import com.aegis.nexus.security.model.User;
import com.aegis.nexus.security.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(user.getRole().getValue())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.isActive())
                .build();
    }

    @Transactional
    public User register(String username, String email, String rawPassword, String organization, Role role) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }

        User user = new User(username, email, passwordEncoder.encode(rawPassword), organization, role);
        return userRepository.save(user);
    }

    public String authenticate(String username, String rawPassword) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, rawPassword)
        );
        UserDetails userDetails = loadUserByUsername(username);
        return jwtService.generateToken(new HashMap<>(), userDetails);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return jwtService.isTokenValid(token, userDetails);
    }

    public String extractUsername(String token) {
        return jwtService.extractUsername(token);
    }
}
