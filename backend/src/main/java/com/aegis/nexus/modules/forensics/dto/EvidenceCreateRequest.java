package com.aegis.nexus.modules.forensics.dto;

public record EvidenceCreateRequest(
    String caseId,
    String fileName,
    String filePath,
    String sha256,
    long fileSize,
    String fileType,
    String metadata,
    String description,
    String collectedBy
) {}
