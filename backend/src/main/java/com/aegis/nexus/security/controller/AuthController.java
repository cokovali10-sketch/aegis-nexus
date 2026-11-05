package com.aegis.nexus.security.controller;

import com.aegis.nexus.security.dto.LoginRequest;
import com.aegis.nexus.security.dto.LoginResponse;
import com.aegis.nexus.security.dto.RegisterRequest;
import com.aegis.nexus.security.dto.UserResponse;
import com.aegis.nexus.security.model.Role;
import com.aegis.nexus.security.model.User;
import com.aegis.nexus.security.service.AuthService;
import com.aegis.nexus.common.exception.PlatformException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authService.authenticate(request.username(), request.password());
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(request.username())
                .password("")
                .authorities("ROLE_ANALYST")
                .build();
        String username = authService.extractUsername(token);
        return ResponseEntity.ok(new LoginResponse(
            token,
            "Bearer",
            username,
            "ROLE_ANALYST"
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        Role role;
        try {
            role = Role.valueOf(request.role().toUpperCase());
        } catch (IllegalArgumentException e) {
            role = Role.ANALYST;
        }

        User user = authService.register(
            request.username(),
            request.email(),
            request.password(),
            request.organization(),
            role
        );

        return ResponseEntity.ok(new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getOrganization(),
            user.getRole().getValue(),
            user.isActive()
        ));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(@RequestHeader(name = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new PlatformException("MISSING_TOKEN", "Authorization Bearer token required", 401);
        }
        String token = authHeader.substring(7);
        String username = authService.extractUsername(token);
        return ResponseEntity.ok(Map.of(
            "username", username,
            "authenticated", true
        ));
    }
}
