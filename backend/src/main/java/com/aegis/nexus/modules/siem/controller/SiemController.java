package com.aegis.nexus.modules.siem.controller;

import com.aegis.nexus.modules.siem.dto.AlertResponse;
import com.aegis.nexus.modules.siem.dto.EventCreateRequest;
import com.aegis.nexus.modules.siem.dto.EventResponse;
import com.aegis.nexus.modules.siem.service.AlertService;
import com.aegis.nexus.modules.siem.service.DetectionService;
import com.aegis.nexus.modules.siem.service.EventService;
import com.aegis.nexus.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/siem")
@PreAuthorize("hasRole('ROLE_ANALYST') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
public class SiemController {

    private final EventService eventService;
    private final AlertService alertService;
    private final DetectionService detectionService;

    public SiemController(EventService eventService, AlertService alertService, DetectionService detectionService) {
        this.eventService = eventService;
        this.alertService = alertService;
        this.detectionService = detectionService;
    }

    // Events
    @PostMapping("/events")
    public ResponseEntity<ApiResponse<EventResponse>> createEvent(@RequestBody EventCreateRequest request) {
        EventResponse response = eventService.createEvent(request);
        return ResponseEntity.ok(ApiResponse.ok(response, "Event created"));
    }

    @GetMapping("/events")
    public ResponseEntity<ApiResponse<List<EventResponse>>> listEvents() {
        List<EventResponse> events = eventService.findAll();
        return ResponseEntity.ok(ApiResponse.ok(events));
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> getEvent(@PathVariable String id) {
        EventResponse response = eventService.findById(id);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/events/recent")
    public ResponseEntity<ApiResponse<List<EventResponse>>> recentEvents(@RequestParam(defaultValue = "60") int minutes) {
        List<EventResponse> events = eventService.findRecent(minutes);
        return ResponseEntity.ok(ApiResponse.ok(events));
    }

    @GetMapping("/events/type/{type}")
    public ResponseEntity<ApiResponse<List<EventResponse>>> listByType(@PathVariable String type) {
        List<EventResponse> events = eventService.findByType(
            com.aegis.nexus.modules.siem.model.EventType.valueOf(type.toUpperCase()));
        return ResponseEntity.ok(ApiResponse.ok(events));
    }

    @GetMapping("/events/severity/{severity}")
    public ResponseEntity<ApiResponse<List<EventResponse>>> listBySeverity(@PathVariable String severity) {
        List<EventResponse> events = eventService.findBySeverity(
            com.aegis.nexus.modules.siem.model.Severity.valueOf(severity.toUpperCase()));
        return ResponseEntity.ok(ApiResponse.ok(events));
    }

    // Alerts
    @GetMapping("/alerts")
    public ResponseEntity<ApiResponse<List<AlertResponse>>> listAlerts() {
        List<AlertResponse> alerts = alertService.findAll();
        return ResponseEntity.ok(ApiResponse.ok(alerts));
    }

    @GetMapping("/alerts/status/{status}")
    public ResponseEntity<ApiResponse<List<AlertResponse>>> listByStatus(@PathVariable String status) {
        List<AlertResponse> alerts = alertService.findByStatus(
            com.aegis.nexus.modules.siem.model.AlertStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(ApiResponse.ok(alerts));
    }

    @GetMapping("/alerts/severity/{severity}")
    public ResponseEntity<ApiResponse<List<AlertResponse>>> listAlertsBySeverity(@PathVariable String severity) {
        List<AlertResponse> alerts = alertService.findBySeverity(
            com.aegis.nexus.modules.siem.model.Severity.valueOf(severity.toUpperCase()));
        return ResponseEntity.ok(ApiResponse.ok(alerts));
    }

    @PostMapping("/alerts/{id}/acknowledge")
    public ResponseEntity<ApiResponse<AlertResponse>> acknowledgeAlert(@PathVariable String id) {
        AlertResponse response = alertService.acknowledge(id);
        return ResponseEntity.ok(ApiResponse.ok(response, "Alert acknowledged"));
    }

    @PostMapping("/alerts/{id}/resolve")
    public ResponseEntity<ApiResponse<AlertResponse>> resolveAlert(@PathVariable String id) {
        AlertResponse response = alertService.resolve(id);
        return ResponseEntity.ok(ApiResponse.ok(response, "Alert resolved"));
    }

    @PostMapping("/alerts/{id}/false-positive")
    public ResponseEntity<ApiResponse<AlertResponse>> markFalsePositive(@PathVariable String id) {
        AlertResponse response = alertService.markFalsePositive(id);
        return ResponseEntity.ok(ApiResponse.ok(response, "Alert marked as false positive"));
    }

    // Stats
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {
        Map<String, Object> stats = Map.of(
            "total_events", eventService.findAll().size(),
            "total_alerts", alertService.findAll().size(),
            "new_alerts", alertService.countByStatus(com.aegis.nexus.modules.siem.model.AlertStatus.NEW),
            "critical_events", eventService.findBySeverity(com.aegis.nexus.modules.siem.model.Severity.CRITICAL).size()
        );
        return ResponseEntity.ok(ApiResponse.ok(stats));
    }
}
