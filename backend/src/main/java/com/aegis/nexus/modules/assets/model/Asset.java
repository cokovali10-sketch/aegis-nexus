package com.aegis.nexus.modules.assets.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "assets")
public class Asset {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "type", nullable = false, length = 50)
    private String type; // SERVER, WORKSTATION, NETWORK_DEVICE, CLOUD_INSTANCE, etc.

    @Column(name = "ip_address", length = 64)
    private String ipAddress;

    @Column(name = "hostname", length = 255)
    private String hostname;

    @Column(name = "os", length = 100)
    private String os;

    @Column(name = "os_version", length = 100)
    private String osVersion;

    @Column(name = "owner", length = 100)
    private String owner;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "risk_score", nullable = false)
    private int riskScore = 0;

    @Column(name = "is_critical", nullable = false)
    private boolean isCritical = false;

    @Column(name = "tags", length = 500)
    private String tags;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    public Asset() {}

    public Asset(String name, String type, String ipAddress, String hostname, String os, String owner) {
        this.name = name;
        this.type = type;
        this.ipAddress = ipAddress;
        this.hostname = hostname;
        this.os = os;
        this.owner = owner;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getHostname() { return hostname; }
    public void setHostname(String hostname) { this.hostname = hostname; }
    public String getOs() { return os; }
    public void setOs(String os) { this.os = os; }
    public String getOsVersion() { return osVersion; }
    public void setOsVersion(String osVersion) { this.osVersion = osVersion; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public int getRiskScore() { return riskScore; }
    public void setRiskScore(int riskScore) { this.riskScore = riskScore; }
    public boolean isCritical() { return isCritical; }
    public void setCritical(boolean critical) { isCritical = critical; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}


