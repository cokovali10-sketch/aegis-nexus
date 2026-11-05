package com.aegis.nexus.modules.siem.dto;

public record EventCreateRequest(
    String eventType,
    String severity,
    String source,
    String hostname,
    String username,
    String ipAddress,
    String description,
    String rawData
) {}


