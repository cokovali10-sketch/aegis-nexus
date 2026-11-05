package com.aegis.nexus.modules.network.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "network_devices")
public class NetworkDevice {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(name = "ip_address", nullable = false, length = 64)
    private String ipAddress;

    @Column(name = "mac_address", length = 32)
    private String macAddress;

    @Column(name = "hostname", length = 255)
    private String hostname;

    @Column(name = "device_type", length = 50)
    private String deviceType; // ROUTER, SWITCH, SERVER, WORKSTATION, IOT, etc.

    @Column(name = "vendor", length = 100)
    private String vendor;

    @Column(name = "os", length = 100)
    private String os;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private DeviceStatus status = DeviceStatus.UNKNOWN;

    @Column(name = "last_seen", nullable = false)
    private Instant lastSeen = Instant.now();

    @Column(name = "is_managed", nullable = false)
    private boolean isManaged = false;

    @Column(name = "risk_score", nullable = false)
    private int riskScore = 0;

    public NetworkDevice() {}

    public NetworkDevice(String ipAddress, String macAddress, String hostname, String deviceType, String vendor) {
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.hostname = hostname;
        this.deviceType = deviceType;
        this.vendor = vendor;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getMacAddress() { return macAddress; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }
    public String getHostname() { return hostname; }
    public void setHostname(String hostname) { this.hostname = hostname; }
    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
    public String getVendor() { return vendor; }
    public void setVendor(String vendor) { this.vendor = vendor; }
    public String getOs() { return os; }
    public void setOs(String os) { this.os = os; }
    public DeviceStatus getStatus() { return status; }
    public void setStatus(DeviceStatus status) { this.status = status; }
    public Instant getLastSeen() { return lastSeen; }
    public void setLastSeen(Instant lastSeen) { this.lastSeen = lastSeen; }
    public boolean isManaged() { return isManaged; }
    public void setManaged(boolean managed) { isManaged = managed; }
    public int getRiskScore() { return riskScore; }
    public void setRiskScore(int riskScore) { this.riskScore = riskScore; }
}
