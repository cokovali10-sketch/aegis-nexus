package com.aegis.nexus.modules.assets.dto;

public record AssetCreateRequest(
    String name,
    String type,
    String ipAddress,
    String hostname,
    String os,
    String osVersion,
    String owner,
    String department,
    String location,
    Integer riskScore,
    Boolean isCritical,
    String tags
) {}
