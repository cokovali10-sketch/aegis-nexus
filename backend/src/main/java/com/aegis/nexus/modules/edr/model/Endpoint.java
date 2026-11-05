package com.aegis.nexus.modules.edr.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "edr_endpoints")
public class Endpoint {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(name = "agent_id", unique = true, nullable = false, length = 100)
    private String agentId;

    @Column(name = "hostname", nullable = false, length = 255)
    private String hostname;

    @Column(name = "ip_address", length = 64)
    private String ipAddress;

    @Column(name = "os", length = 100)
    private String os;

    @Column(name = "os_version", length = 100)
    private String osVersion;

    @Column(name = "agent_version", length = 50)
    private String agentVersion;

    @Enumerated(EnumType.STRING)
        @Column(name = "status", columnDefinition = "varchar(50)", nullable = false, length = 20)
    private EndpointStatus status = EndpointStatus.OFFLINE;

    @Column(name = "last_seen_at")
    private Instant lastSeenAt;

    @Column(name = "risk_score", nullable = false)
    private int riskScore = 0;

    @Column(name = "is_isolated", nullable = false)
    private boolean isIsolated = false;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    public Endpoint() {}

    public Endpoint(String agentId, String hostname, String ipAddress, String os, String osVersion, String agentVersion) {
        this.agentId = agentId;
        this.hostname = hostname;
        this.ipAddress = ipAddress;
        this.os = os;
        this.osVersion = osVersion;
        this.agentVersion = agentVersion;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }
    public String getHostname() { return hostname; }
    public void setHostname(String hostname) { this.hostname = hostname; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getOs() { return os; }
    public void setOs(String os) { this.os = os; }
    public String getOsVersion() { return osVersion; }
    public void setOsVersion(String osVersion) { this.osVersion = osVersion; }
    public String getAgentVersion() { return agentVersion; }
    public void setAgentVersion(String agentVersion) { this.agentVersion = agentVersion; }
    public EndpointStatus getStatus() { return status; }
    public void setStatus(EndpointStatus status) { this.status = status; }
    public Instant getLastSeenAt() { return lastSeenAt; }
    public void setLastSeenAt(Instant lastSeenAt) { this.lastSeenAt = lastSeenAt; }
    public int getRiskScore() { return riskScore; }
    public void setRiskScore(int riskScore) { this.riskScore = riskScore; }
    public boolean isIsolated() { return isIsolated; }
    public void setIsolated(boolean isolated) { isIsolated = isolated; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}


