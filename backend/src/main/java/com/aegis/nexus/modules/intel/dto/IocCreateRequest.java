package com.aegis.nexus.modules.intel.dto;

public record IocCreateRequest(
    String type,
    String value,
    String source,
    Integer confidence,
    String threatType,
    String description
) {}
