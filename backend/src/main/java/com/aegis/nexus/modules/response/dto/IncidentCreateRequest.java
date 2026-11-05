package com.aegis.nexus.modules.response.dto;

public record IncidentCreateRequest(
    String title,
    String description,
    String severity,
    String hostname,
    String assignee,
    String notes
) {}
