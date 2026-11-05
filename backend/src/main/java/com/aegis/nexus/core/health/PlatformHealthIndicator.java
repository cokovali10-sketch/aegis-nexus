package com.aegis.nexus.core.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class PlatformHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean healthy = true;
        if (healthy) {
            return Health.up().withDetail("platform", "AEGIS NEXUS").withDetail("status", "operational").build();
        }
        return Health.down().withDetail("platform", "AEGIS NEXUS").withDetail("status", "degraded").build();
    }
}
