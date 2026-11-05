package com.aegis.nexus.modules.assets.dto;

public record AssetResponse(
    String id,
    String name,
    String type,
    String ipAddress,
    String hostname,
    String os,
    String osVersion,
    String owner,
    String department,
    String location,
    int riskScore,
    boolean isCritical,
    String tags,
    String createdAt,
    String updatedAt
) {}
