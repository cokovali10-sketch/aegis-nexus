package com.aegis.nexus.modules.automation.dto;

public record WorkflowCreateRequest(
    String name,
    String description,
    String triggerType,
    String triggerConfig,
    String conditions,
    String actions,
    Boolean isEnabled
) {}


