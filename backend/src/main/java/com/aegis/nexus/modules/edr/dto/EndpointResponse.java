package com.aegis.nexus.modules.edr.dto;

public record EndpointResponse(
    String id,
    String agentId,
    String hostname,
    String ipAddress,
    String os,
    String osVersion,
    String agentVersion,
    String status,
    String lastSeenAt,
    int riskScore,
    boolean isIsolated
) {}
