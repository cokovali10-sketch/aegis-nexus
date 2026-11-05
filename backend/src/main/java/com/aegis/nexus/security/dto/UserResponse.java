package com.aegis.nexus.security.dto;

public record UserResponse(
    String id,
    String username,
    String email,
    String organization,
    String role,
    boolean active
) {}
