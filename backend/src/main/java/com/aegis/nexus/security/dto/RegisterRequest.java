package com.aegis.nexus.security.dto;

public record RegisterRequest(
    String username,
    String email,
    String password,
    String organization,
    String role
) {}
