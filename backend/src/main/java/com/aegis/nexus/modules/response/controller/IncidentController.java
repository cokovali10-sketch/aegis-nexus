package com.aegis.nexus.modules.response.controller;

import com.aegis.nexus.modules.response.dto.IncidentCreateRequest;
import com.aegis.nexus.modules.response.dto.IncidentResponse;
import com.aegis.nexus.modules.response.service.IncidentService;
import com.aegis.nexus.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/response")
@PreAuthorize("hasRole('ROLE_ANALYST') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping("/incidents")
    public ResponseEntity<ApiResponse<IncidentResponse>> createIncident(@RequestBody IncidentCreateRequest request) {
        IncidentResponse response = incidentService.createIncident(request);
        return ResponseEntity.ok(ApiResponse.ok(response, "Incident created"));
    }

    @GetMapping("/incidents")
    public ResponseEntity<ApiResponse<List<IncidentResponse>>> listIncidents() {
        List<IncidentResponse> incidents = incidentService.findAll();
        return ResponseEntity.ok(ApiResponse.ok(incidents));
    }

    @GetMapping("/incidents/{id}")
    public ResponseEntity<ApiResponse<IncidentResponse>> getIncident(@PathVariable String id) {
        IncidentResponse response = incidentService.findById(id);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/incidents/status/{status}")
    public ResponseEntity<ApiResponse<List<IncidentResponse>>> listByStatus(@PathVariable String status) {
        List<IncidentResponse> incidents = incidentService.findByStatus(
            com.aegis.nexus.modules.response.model.IncidentStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(ApiResponse.ok(incidents));
    }

    @GetMapping("/incidents/severity/{severity}")
    public ResponseEntity<ApiResponse<List<IncidentResponse>>> listBySeverity(@PathVariable String severity) {
        List<IncidentResponse> incidents = incidentService.findBySeverity(
            com.aegis.nexus.modules.response.model.Severity.valueOf(severity.toUpperCase()));
        return ResponseEntity.ok(ApiResponse.ok(incidents));
    }

    @PostMapping("/incidents/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<IncidentResponse>> updateStatus(@PathVariable String id, @RequestParam String status) {
        IncidentResponse response = incidentService.updateStatus(id,
            com.aegis.nexus.modules.response.model.IncidentStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(ApiResponse.ok(response, "Status updated"));
    }

    @PostMapping("/incidents/{id}/assign")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<IncidentResponse>> assign(@PathVariable String id, @RequestParam String assignee) {
        IncidentResponse response = incidentService.assign(id, assignee);
        return ResponseEntity.ok(ApiResponse.ok(response, "Assignee updated"));
    }

    @PostMapping("/incidents/{id}/notes")
    public ResponseEntity<ApiResponse<IncidentResponse>> addNotes(@PathVariable String id, @RequestParam String notes) {
        IncidentResponse response = incidentService.addNotes(id, notes);
        return ResponseEntity.ok(ApiResponse.ok(response, "Notes added"));
    }
}


