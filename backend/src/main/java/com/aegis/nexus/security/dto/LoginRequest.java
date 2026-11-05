package com.aegis.nexus.security.dto;

public record LoginRequest(
    String username,
    String password
) {}
