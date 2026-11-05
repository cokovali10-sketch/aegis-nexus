package com.aegis.nexus.modules.edr.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "edr_file_integrity")
public class FileIntegrityRecord {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(name = "endpoint_id", nullable = false)
    private String endpointId;

    @Column(name = "file_path", nullable = false, length = 1000)
    private String filePath;

    @Column(name = "sha256", nullable = false, length = 64)
    private String sha256;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "change_type", nullable = false, length = 50)
    private String changeType; // CREATED, MODIFIED, DELETED

    @Column(name = "detected_at", nullable = false)
    private Instant detectedAt = Instant.now();

    @Column(name = "is_authorized", nullable = false)
    private boolean isAuthorized = false;

    public FileIntegrityRecord() {}

    public FileIntegrityRecord(String endpointId, String filePath, String sha256, long fileSize, String changeType) {
        this.endpointId = endpointId;
        this.filePath = filePath;
        this.sha256 = sha256;
        this.fileSize = fileSize;
        this.changeType = changeType;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEndpointId() { return endpointId; }
    public void setEndpointId(String endpointId) { this.endpointId = endpointId; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public String getSha256() { return sha256; }
    public void setSha256(String sha256) { this.sha256 = sha256; }
    public long getFileSize() { return fileSize; }
    public void setFileSize(long fileSize) { this.fileSize = fileSize; }
    public String getChangeType() { return changeType; }
    public void setChangeType(String changeType) { this.changeType = changeType; }
    public Instant getDetectedAt() { return detectedAt; }
    public void setDetectedAt(Instant detectedAt) { this.detectedAt = detectedAt; }
    public boolean isAuthorized() { return isAuthorized; }
    public void setAuthorized(boolean authorized) { isAuthorized = authorized; }
}


