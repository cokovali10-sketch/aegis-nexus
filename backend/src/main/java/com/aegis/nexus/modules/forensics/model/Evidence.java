package com.aegis.nexus.modules.forensics.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "forensics_evidence")
public class Evidence {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(name = "case_id", nullable = false, length = 100)
    private String caseId;

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Column(name = "file_path", length = 1000)
    private String filePath;

    @Column(name = "sha256", nullable = false, length = 64)
    private String sha256;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "file_type", length = 100)
    private String fileType;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "collected_at", nullable = false)
    private Instant collectedAt = Instant.now();

    @Column(name = "collected_by", length = 100)
    private String collectedBy;

    @Column(name = "integrity_verified", nullable = false)
    private boolean integrityVerified = false;

    public Evidence() {}

    public Evidence(String caseId, String fileName, String filePath, String sha256, long fileSize, String fileType, String description, String collectedBy) {
        this.caseId = caseId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.sha256 = sha256;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.description = description;
        this.collectedBy = collectedBy;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCaseId() { return caseId; }
    public void setCaseId(String caseId) { this.caseId = caseId; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public String getSha256() { return sha256; }
    public void setSha256(String sha256) { this.sha256 = sha256; }
    public long getFileSize() { return fileSize; }
    public void setFileSize(long fileSize) { this.fileSize = fileSize; }
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Instant getCollectedAt() { return collectedAt; }
    public void setCollectedAt(Instant collectedAt) { this.collectedAt = collectedAt; }
    public String getCollectedBy() { return collectedBy; }
    public void setCollectedBy(String collectedBy) { this.collectedBy = collectedBy; }
    public boolean isIntegrityVerified() { return integrityVerified; }
    public void setIntegrityVerified(boolean integrityVerified) { this.integrityVerified = integrityVerified; }
}


