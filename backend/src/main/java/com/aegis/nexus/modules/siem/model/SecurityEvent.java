package com.aegis.nexus.modules.siem.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "siem_events")
public class SecurityEvent {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp = Instant.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 50)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, length = 20)
    private Severity severity = Severity.INFO;

    @Column(name = "source", length = 255)
    private String source;

    @Column(name = "hostname", length = 255)
    private String hostname;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "ip_address", length = 64)
    private String ipAddress;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "raw_data", columnDefinition = "TEXT")
    private String rawData;

    @Column(name = "normalized_data", columnDefinition = "TEXT")
    private String normalizedData;

    @Column(name = "correlation_id", length = 100)
    private String correlationId;

    @Column(name = "is_alert", nullable = false)
    private boolean isAlert = false;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    public SecurityEvent() {}

    public SecurityEvent(EventType eventType, Severity severity, String source, String description) {
        this.eventType = eventType;
        this.severity = severity;
        this.source = source;
        this.description = description;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public EventType getEventType() { return eventType; }
    public void setEventType(EventType eventType) { this.eventType = eventType; }
    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getHostname() { return hostname; }
    public void setHostname(String hostname) { this.hostname = hostname; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getRawData() { return rawData; }
    public void setRawData(String rawData) { this.rawData = rawData; }
    public String getNormalizedData() { return normalizedData; }
    public void setNormalizedData(String normalizedData) { this.normalizedData = normalizedData; }
    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
    public boolean isAlert() { return isAlert; }
    public void setAlert(boolean alert) { isAlert = alert; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
