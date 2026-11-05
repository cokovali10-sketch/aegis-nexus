package com.aegis.nexus.modules.edr.dto;

public record EndpointRegisterRequest(
    String agentId,
    String hostname,
    String ipAddress,
    String os,
    String osVersion,
    String agentVersion
) {}


