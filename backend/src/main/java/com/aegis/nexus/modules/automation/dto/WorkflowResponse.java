package com.aegis.nexus.modules.automation.dto;

public record WorkflowResponse(
    String id,
    String name,
    String description,
    String triggerType,
    String triggerConfig,
    String conditions,
    String actions,
    boolean isEnabled,
    int executionCount,
    String lastExecutedAt,
    String createdAt,
    String updatedAt
) {}


