package com.aegis.nexus.modules.edr.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "edr_processes")
public class Process {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(name = "endpoint_id", nullable = false)
    private String endpointId;

    @Column(name = "pid", nullable = false)
    private int pid;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "executable_path", length = 1000)
    private String executablePath;

    @Column(name = "command_line", length = 2000)
    private String commandLine;

    @Column(name = "parent_pid")
    private Integer parentPid;

    @Column(name = "process_user", length = 100)
    private String user;

    @Column(name = "cpu_usage")
    private double cpuUsage;

    @Column(name = "memory_usage_mb")
    private double memoryUsageMB;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "is_suspicious", nullable = false)
    private boolean isSuspicious = false;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    public Process() {}

    public Process(String endpointId, int pid, String name, String executablePath, String commandLine, Integer parentPid, String user) {
        this.endpointId = endpointId;
        this.pid = pid;
        this.name = name;
        this.executablePath = executablePath;
        this.commandLine = commandLine;
        this.parentPid = parentPid;
        this.user = user;
        this.startedAt = Instant.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEndpointId() { return endpointId; }
    public void setEndpointId(String endpointId) { this.endpointId = endpointId; }
    public int getPid() { return pid; }
    public void setPid(int pid) { this.pid = pid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getExecutablePath() { return executablePath; }
    public void setExecutablePath(String executablePath) { this.executablePath = executablePath; }
    public String getCommandLine() { return commandLine; }
    public void setCommandLine(String commandLine) { this.commandLine = commandLine; }
    public Integer getParentPid() { return parentPid; }
    public void setParentPid(Integer parentPid) { this.parentPid = parentPid; }
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public double getCpuUsage() { return cpuUsage; }
    public void setCpuUsage(double cpuUsage) { this.cpuUsage = cpuUsage; }
    public double getMemoryUsageMB() { return memoryUsageMB; }
    public void setMemoryUsageMB(double memoryUsageMB) { this.memoryUsageMB = memoryUsageMB; }
    public Instant getStartedAt() { return startedAt; }
    public void setStartedAt(Instant startedAt) { this.startedAt = startedAt; }
    public boolean isSuspicious() { return isSuspicious; }
    public void setSuspicious(boolean suspicious) { isSuspicious = suspicious; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}


