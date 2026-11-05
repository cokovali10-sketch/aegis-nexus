package com.aegis.nexus.modules.ai.controller;

import com.aegis.nexus.modules.ai.service.AiAnalystService;
import com.aegis.nexus.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/ai")
@PreAuthorize("hasRole('ROLE_ANALYST') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
public class AiAnalystController {

    private final AiAnalystService aiAnalystService;

    public AiAnalystController(AiAnalystService aiAnalystService) {
        this.aiAnalystService = aiAnalystService;
    }

    @GetMapping("/analyze-event/{eventId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> analyzeEvent(@PathVariable String eventId) {
        Map<String, Object> analysis = aiAnalystService.analyzeEvent(eventId);
        return ResponseEntity.ok(ApiResponse.ok(analysis));
    }

    @GetMapping("/summarize-incident/{incidentId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> summarizeIncident(@PathVariable String incidentId) {
        Map<String, Object> summary = aiAnalystService.summarizeIncident(incidentId);
        return ResponseEntity.ok(ApiResponse.ok(summary));
    }

    @GetMapping("/explain-osint")
    public ResponseEntity<ApiResponse<Map<String, Object>>> explainOsint(@RequestParam String type, @RequestParam String value) {
        Map<String, Object> explanation = aiAnalystService.explainOsintResult(type, value);
        return ResponseEntity.ok(ApiResponse.ok(explanation));
    }

    @GetMapping("/suggest-steps")
    public ResponseEntity<ApiResponse<Map<String, Object>>> suggestSteps(@RequestParam String context) {
        Map<String, Object> suggestions = aiAnalystService.suggestInvestigationSteps(context);
        return ResponseEntity.ok(ApiResponse.ok(suggestions));
    }

    @GetMapping("/overview")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getOverview() {
        Map<String, Object> overview = aiAnalystService.getPlatformOverview();
        return ResponseEntity.ok(ApiResponse.ok(overview));
    }
}
