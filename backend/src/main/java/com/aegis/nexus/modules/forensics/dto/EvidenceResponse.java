package com.aegis.nexus.modules.forensics.dto;

public record EvidenceResponse(
    String id,
    String caseId,
    String fileName,
    String filePath,
    String sha256,
    long fileSize,
    String fileType,
    String metadata,
    String description,
    String collectedAt,
    String collectedBy,
    boolean integrityVerified
) {}


