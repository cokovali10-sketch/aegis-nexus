package com.aegis.nexus.modules.siem.dto;

public record AlertResponse(
    String id,
    String title,
    String description,
    String severity,
    String status,
    String source,
    String eventId,
    String ruleName,
    String hostname,
    String assignee,
    String createdAt,
    String updatedAt,
    String resolvedAt
) {}
