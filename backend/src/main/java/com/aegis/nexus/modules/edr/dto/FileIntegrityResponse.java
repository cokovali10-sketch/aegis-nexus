package com.aegis.nexus.modules.edr.dto;

public record FileIntegrityResponse(
    String id,
    String endpointId,
    String filePath,
    String sha256,
    long fileSize,
    String changeType,
    String detectedAt,
    boolean isAuthorized
) {}
