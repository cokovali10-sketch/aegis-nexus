package com.aegis.nexus.modules.siem.service;

import com.aegis.nexus.modules.siem.model.EventType;
import com.aegis.nexus.modules.siem.model.Severity;
import com.aegis.nexus.modules.siem.model.SecurityEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DetectionService {

    public List<DetectionResult> evaluateEvent(SecurityEvent event) {
        List<DetectionResult> results = new ArrayList<>();

        // Rule 1: Brute force detection
        if (event.getEventType() == EventType.AUTH_FAILURE) {
            results.add(new DetectionResult("Brute Force Detection", "Multiple authentication failures detected", Severity.HIGH));
        }

        // Rule 2: Privilege escalation
        if (event.getEventType() == EventType.PRIVILEGE_ESCALATION) {
            results.add(new DetectionResult("Privilege Escalation", "Privilege escalation attempt detected", Severity.CRITICAL));
        }

        // Rule 3: Malware detection
        if (event.getEventType() == EventType.MALWARE_DETECTED) {
            results.add(new DetectionResult("Malware Detection", "Malware detected on endpoint", Severity.CRITICAL));
        }

        // Rule 4: Suspicious behavior
        if (event.getEventType() == EventType.SUSPICIOUS_BEHAVIOR) {
            results.add(new DetectionResult("Suspicious Behavior", "Suspicious activity detected", Severity.MEDIUM));
        }

        // Rule 5: Policy violation
        if (event.getEventType() == EventType.POLICY_VIOLATION) {
            results.add(new DetectionResult("Policy Violation", "Security policy violation", Severity.MEDIUM));
        }

        return results;
    }

    public record DetectionResult(String ruleName, String description, Severity severity) {}
}
