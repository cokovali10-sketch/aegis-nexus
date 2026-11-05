package com.aegis.nexus.modules.siem;

import com.aegis.nexus.modules.siem.model.SecurityEvent;
import com.aegis.nexus.modules.siem.model.EventType;
import com.aegis.nexus.modules.siem.model.Severity;
import com.aegis.nexus.modules.siem.service.DetectionService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DetectionServiceTest {

    private final DetectionService detectionService = new DetectionService();

    @Test
    void detectsBruteForce() {
        SecurityEvent event = new SecurityEvent(EventType.AUTH_FAILURE, Severity.INFO, "SIEM", "Failed login");
        var results = detectionService.evaluateEvent(event);
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).ruleName()).isEqualTo("Brute Force Detection");
    }

    @Test
    void detectsPrivilegeEscalation() {
        SecurityEvent event = new SecurityEvent(EventType.PRIVILEGE_ESCALATION, Severity.INFO, "SIEM", "Privilege escalation");
        var results = detectionService.evaluateEvent(event);
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).severity()).isEqualTo(Severity.CRITICAL);
    }

    @Test
    void detectsMalware() {
        SecurityEvent event = new SecurityEvent(EventType.MALWARE_DETECTED, Severity.INFO, "EDR", "Malware found");
        var results = detectionService.evaluateEvent(event);
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).ruleName()).isEqualTo("Malware Detection");
    }

    @Test
    void noDetectionForInfoEvent() {
        SecurityEvent event = new SecurityEvent(EventType.AUTH_SUCCESS, Severity.INFO, "SIEM", "Login success");
        var results = detectionService.evaluateEvent(event);
        assertThat(results).isEmpty();
    }

    @Test
    void detectsSuspiciousBehavior() {
        SecurityEvent event = new SecurityEvent(EventType.SUSPICIOUS_BEHAVIOR, Severity.INFO, "EDR", "Suspicious activity");
        var results = detectionService.evaluateEvent(event);
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).severity()).isEqualTo(Severity.MEDIUM);
    }
}
