package com.aegis.nexus.modules.network.dto;

public record NetworkDeviceCreateRequest(
    String ipAddress,
    String macAddress,
    String hostname,
    String deviceType,
    String vendor,
    String os,
    Boolean isManaged,
    Integer riskScore
) {}
