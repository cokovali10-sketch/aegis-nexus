package com.aegis.nexus.modules.siem.dto;

public record EventResponse(
    String id,
    String timestamp,
    String eventType,
    String severity,
    String source,
    String hostname,
    String username,
    String ipAddress,
    String description,
    String correlationId,
    boolean isAlert
) {}


