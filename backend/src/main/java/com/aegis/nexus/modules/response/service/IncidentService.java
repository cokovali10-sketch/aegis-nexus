package com.aegis.nexus.modules.response.service;

import com.aegis.nexus.modules.response.dto.IncidentCreateRequest;
import com.aegis.nexus.modules.response.dto.IncidentResponse;
import com.aegis.nexus.modules.response.model.Incident;
import com.aegis.nexus.modules.response.model.IncidentStatus;
import com.aegis.nexus.modules.response.model.Severity;
import com.aegis.nexus.modules.response.repository.IncidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @Transactional
    public IncidentResponse createIncident(IncidentCreateRequest request) {
        Severity severity;
        try {
            severity = Severity.valueOf(request.severity().toUpperCase());
        } catch (IllegalArgumentException e) {
            severity = Severity.MEDIUM;
        }

        Incident incident = new Incident(request.title(), request.description(), severity, request.hostname());
        incident.setAssignee(request.assignee());
        incident.setNotes(request.notes());
        incident = incidentRepository.save(incident);
        return toResponse(incident);
    }

    public IncidentResponse findById(String id) {
        return incidentRepository.findById(id).map(this::toResponse).orElse(null);
    }

    public List<IncidentResponse> findAll() {
        return incidentRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<IncidentResponse> findByStatus(IncidentStatus status) {
        return incidentRepository.findByStatus(status).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<IncidentResponse> findBySeverity(Severity severity) {
        return incidentRepository.findBySeverity(severity).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public IncidentResponse updateStatus(String id, IncidentStatus status) {
        Incident incident = incidentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Incident not found: " + id));
        incident.setStatus(status);
        incident.setUpdatedAt(Instant.now());
        if (status == IncidentStatus.RESOLVED || status == IncidentStatus.CLOSED) {
            incident.setResolvedAt(Instant.now());
        }
        incident = incidentRepository.save(incident);
        return toResponse(incident);
    }

    @Transactional
    public IncidentResponse assign(String id, String assignee) {
        Incident incident = incidentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Incident not found: " + id));
        incident.setAssignee(assignee);
        incident.setUpdatedAt(Instant.now());
        incident = incidentRepository.save(incident);
        return toResponse(incident);
    }

    @Transactional
    public IncidentResponse addNotes(String id, String notes) {
        Incident incident = incidentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Incident not found: " + id));
        String existing = incident.getNotes();
        incident.setNotes(existing != null ? existing + "\n" + notes : notes);
        incident.setUpdatedAt(Instant.now());
        incident = incidentRepository.save(incident);
        return toResponse(incident);
    }

    public long countByStatus(IncidentStatus status) {
        return incidentRepository.findByStatus(status).size();
    }

    private IncidentResponse toResponse(Incident i) {
        return new IncidentResponse(
            i.getId(), i.getTitle(), i.getDescription(), i.getSeverity().name(), i.getStatus().name(),
            i.getAssignee(), i.getHostname(),
            i.getCreatedAt().toString(),
            i.getUpdatedAt() != null ? i.getUpdatedAt().toString() : null,
            i.getResolvedAt() != null ? i.getResolvedAt().toString() : null,
            i.getNotes()
        );
    }
}


