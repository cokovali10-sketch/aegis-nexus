package com.aegis.nexus.modules.siem.service;

import com.aegis.nexus.modules.siem.dto.AlertResponse;
import com.aegis.nexus.modules.siem.model.Alert;
import com.aegis.nexus.modules.siem.model.AlertStatus;
import com.aegis.nexus.modules.siem.model.Severity;
import com.aegis.nexus.modules.siem.repository.AlertRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Transactional
    public AlertResponse createAlert(String title, String description, Severity severity, String source,
                                    String eventId, String ruleName, String hostname) {
        Alert alert = new Alert(title, description, severity, source, eventId, ruleName, hostname);
        alert = alertRepository.save(alert);
        return toResponse(alert);
    }

    public AlertResponse findById(String id) {
        return alertRepository.findById(id).map(this::toResponse).orElse(null);
    }

    public List<AlertResponse> findAll() {
        return alertRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<AlertResponse> findByStatus(AlertStatus status) {
        return alertRepository.findByStatus(status).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<AlertResponse> findBySeverity(Severity severity) {
        return alertRepository.findBySeverity(severity).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public AlertResponse acknowledge(String id) {
        Alert alert = alertRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + id));
        alert.setStatus(AlertStatus.ACKNOWLEDGED);
        alert.setUpdatedAt(Instant.now());
        alert = alertRepository.save(alert);
        return toResponse(alert);
    }

    @Transactional
    public AlertResponse resolve(String id) {
        Alert alert = alertRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + id));
        alert.setStatus(AlertStatus.RESOLVED);
        alert.setResolvedAt(Instant.now());
        alert.setUpdatedAt(Instant.now());
        alert = alertRepository.save(alert);
        return toResponse(alert);
    }

    @Transactional
    public AlertResponse markFalsePositive(String id) {
        Alert alert = alertRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + id));
        alert.setStatus(AlertStatus.FALSE_POSITIVE);
        alert.setUpdatedAt(Instant.now());
        alert = alertRepository.save(alert);
        return toResponse(alert);
    }

    public long countByStatus(AlertStatus status) {
        return alertRepository.findByStatus(status).size();
    }

    private AlertResponse toResponse(Alert a) {
        return new AlertResponse(
            a.getId(),
            a.getTitle(),
            a.getDescription(),
            a.getSeverity().name(),
            a.getStatus().name(),
            a.getSource(),
            a.getEventId(),
            a.getRuleName(),
            a.getHostname(),
            a.getAssignee(),
            a.getCreatedAt().toString(),
            a.getUpdatedAt() != null ? a.getUpdatedAt().toString() : null,
            a.getResolvedAt() != null ? a.getResolvedAt().toString() : null
        );
    }
}


