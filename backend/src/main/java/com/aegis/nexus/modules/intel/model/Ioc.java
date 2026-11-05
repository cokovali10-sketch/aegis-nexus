package com.aegis.nexus.modules.intel.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "intel_iocs", uniqueConstraints = @UniqueConstraint(columnNames = {"ioc_type", "ioc_value"}))
public class Ioc {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Enumerated(EnumType.STRING)
        @Column(name = "ioc_type", columnDefinition = "varchar(50)", nullable = false, length = 30)
    private IocType type;

    @Column(name = "ioc_value", nullable = false, length = 500)
    private String value;

    @Column(name = "source", length = 255)
    private String source;

    @Column(name = "confidence", nullable = false)
    private int confidence = 50;

    @Enumerated(EnumType.STRING)
        @Column(name = "threat_type", columnDefinition = "varchar(50)", length = 50)
    private ThreatType threatType;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "first_seen", nullable = false)
    private Instant firstSeen = Instant.now();

    @Column(name = "last_seen")
    private Instant lastSeen;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    public Ioc() {}

    public Ioc(IocType type, String value, String source, int confidence, ThreatType threatType, String description) {
        this.type = type;
        this.value = value;
        this.source = source;
        this.confidence = confidence;
        this.threatType = threatType;
        this.description = description;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public IocType getType() { return type; }
    public void setType(IocType type) { this.type = type; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public int getConfidence() { return confidence; }
    public void setConfidence(int confidence) { this.confidence = confidence; }
    public ThreatType getThreatType() { return threatType; }
    public void setThreatType(ThreatType threatType) { this.threatType = threatType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Instant getFirstSeen() { return firstSeen; }
    public void setFirstSeen(Instant firstSeen) { this.firstSeen = firstSeen; }
    public Instant getLastSeen() { return lastSeen; }
    public void setLastSeen(Instant lastSeen) { this.lastSeen = lastSeen; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}


