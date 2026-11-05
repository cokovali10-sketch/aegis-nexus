package com.aegis.nexus.modules.forensics.controller;

import com.aegis.nexus.modules.forensics.dto.EvidenceCreateRequest;
import com.aegis.nexus.modules.forensics.dto.EvidenceResponse;
import com.aegis.nexus.modules.forensics.service.EvidenceService;
import com.aegis.nexus.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/forensics")
@PreAuthorize("hasRole('ROLE_ANALYST') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
public class EvidenceController {

    private final EvidenceService evidenceService;

    public EvidenceController(EvidenceService evidenceService) {
        this.evidenceService = evidenceService;
    }

    @PostMapping("/evidence")
    public ResponseEntity<ApiResponse<EvidenceResponse>> createEvidence(@RequestBody EvidenceCreateRequest request) {
        EvidenceResponse response = evidenceService.createEvidence(request);
        return ResponseEntity.ok(ApiResponse.ok(response, "Evidence collected"));
    }

    @GetMapping("/evidence")
    public ResponseEntity<ApiResponse<List<EvidenceResponse>>> listEvidence() {
        List<EvidenceResponse> evidence = evidenceService.findAll();
        return ResponseEntity.ok(ApiResponse.ok(evidence));
    }

    @GetMapping("/evidence/{id}")
    public ResponseEntity<ApiResponse<EvidenceResponse>> getEvidence(@PathVariable String id) {
        EvidenceResponse response = evidenceService.findById(id);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/evidence/case/{caseId}")
    public ResponseEntity<ApiResponse<List<EvidenceResponse>>> listByCase(@PathVariable String caseId) {
        List<EvidenceResponse> evidence = evidenceService.findByCaseId(caseId);
        return ResponseEntity.ok(ApiResponse.ok(evidence));
    }

    @PostMapping("/evidence/{id}/verify")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<EvidenceResponse>> verifyIntegrity(@PathVariable String id) {
        EvidenceResponse response = evidenceService.verifyIntegrity(id);
        return ResponseEntity.ok(ApiResponse.ok(response, "Integrity verified"));
    }
}
