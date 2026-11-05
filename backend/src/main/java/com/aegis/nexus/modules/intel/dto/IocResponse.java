package com.aegis.nexus.modules.intel.dto;

public record IocResponse(
    String id,
    String type,
    String value,
    String source,
    int confidence,
    String threatType,
    String description,
    String firstSeen,
    String lastSeen,
    boolean isActive
) {}


