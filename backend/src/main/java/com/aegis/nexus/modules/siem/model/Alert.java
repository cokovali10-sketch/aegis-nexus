package com.aegis.nexus.modules.siem.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "siem_alerts")
public class Alert {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
        @Column(name = "severity", columnDefinition = "varchar(50)", nullable = false, length = 20)
    private Severity severity;

    @Enumerated(EnumType.STRING)
        @Column(name = "status", columnDefinition = "varchar(50)", nullable = false, length = 20)
    private AlertStatus status = AlertStatus.NEW;

    @Column(name = "source", length = 255)
    private String source;

    @Column(name = "event_id", length = 100)
    private String eventId;

    @Column(name = "rule_name", length = 255)
    private String ruleName;

    @Column(name = "hostname", length = 255)
    private String hostname;

    @Column(name = "assignee", length = 100)
    private String assignee;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "resolved_at")
    private Instant resolvedAt;

    public Alert() {}

    public Alert(String title, String description, Severity severity, String source, String eventId, String ruleName, String hostname) {
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.source = source;
        this.eventId = eventId;
        this.ruleName = ruleName;
        this.hostname = hostname;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }
    public AlertStatus getStatus() { return status; }
    public void setStatus(AlertStatus status) { this.status = status; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
    public String getHostname() { return hostname; }
    public void setHostname(String hostname) { this.hostname = hostname; }
    public String getAssignee() { return assignee; }
    public void setAssignee(String assignee) { this.assignee = assignee; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    public Instant getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(Instant resolvedAt) { this.resolvedAt = resolvedAt; }
}


