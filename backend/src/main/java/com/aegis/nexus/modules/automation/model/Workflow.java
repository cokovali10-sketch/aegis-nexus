package com.aegis.nexus.modules.automation.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "automation_workflows")
public class Workflow {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "trigger_type", nullable = false, length = 50)
    private String triggerType; // EVENT, SCHEDULE, MANUAL

    @Column(name = "trigger_config", columnDefinition = "TEXT")
    private String triggerConfig;

    @Column(name = "conditions", columnDefinition = "TEXT")
    private String conditions;

    @Column(name = "actions", columnDefinition = "TEXT")
    private String actions;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled = true;

    @Column(name = "execution_count", nullable = false)
    private int executionCount = 0;

    @Column(name = "last_executed_at")
    private Instant lastExecutedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    public Workflow() {}

    public Workflow(String name, String description, String triggerType, String triggerConfig, String conditions, String actions) {
        this.name = name;
        this.description = description;
        this.triggerType = triggerType;
        this.triggerConfig = triggerConfig;
        this.conditions = conditions;
        this.actions = actions;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTriggerType() { return triggerType; }
    public void setTriggerType(String triggerType) { this.triggerType = triggerType; }
    public String getTriggerConfig() { return triggerConfig; }
    public void setTriggerConfig(String triggerConfig) { this.triggerConfig = triggerConfig; }
    public String getConditions() { return conditions; }
    public void setConditions(String conditions) { this.conditions = conditions; }
    public String getActions() { return actions; }
    public void setActions(String actions) { this.actions = actions; }
    public boolean isEnabled() { return isEnabled; }
    public void setEnabled(boolean enabled) { isEnabled = enabled; }
    public int getExecutionCount() { return executionCount; }
    public void setExecutionCount(int executionCount) { this.executionCount = executionCount; }
    public Instant getLastExecutedAt() { return lastExecutedAt; }
    public void setLastExecutedAt(Instant lastExecutedAt) { this.lastExecutedAt = lastExecutedAt; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}


