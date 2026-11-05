package com.aegis.nexus.modules.response.dto;

public record IncidentResponse(
    String id,
    String title,
    String description,
    String severity,
    String status,
    String assignee,
    String hostname,
    String createdAt,
    String updatedAt,
    String resolvedAt,
    String notes
) {}
