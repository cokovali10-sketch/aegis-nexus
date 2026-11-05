package com.aegis.nexus.modules.network.dto;

public record NetworkDeviceResponse(
    String id,
    String ipAddress,
    String macAddress,
    String hostname,
    String deviceType,
    String vendor,
    String os,
    String status,
    String lastSeen,
    boolean isManaged,
    int riskScore
) {}
