package com.aegis.nexus.modules.edr.dto;

public record ProcessResponse(
    String id,
    String endpointId,
    int pid,
    String name,
    String executablePath,
    String commandLine,
    Integer parentPid,
    String user,
    double cpuUsage,
    double memoryUsageMB,
    String startedAt,
    boolean isSuspicious
) {}
