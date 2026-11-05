package com.aegis.nexus.modules.reports.controller;

import com.aegis.nexus.modules.reports.service.ReportService;
import com.aegis.nexus.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reports")
@PreAuthorize("hasRole('ROLE_ANALYST') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/executive-summary")
    public ResponseEntity<ApiResponse<Map<String, Object>>> executiveSummary() {
        Map<String, Object> report = reportService.generateExecutiveSummary();
        return ResponseEntity.ok(ApiResponse.ok(report));
    }

    @GetMapping("/technical")
    public ResponseEntity<ApiResponse<Map<String, Object>>> technicalReport() {
        Map<String, Object> report = reportService.generateTechnicalReport();
        return ResponseEntity.ok(ApiResponse.ok(report));
    }
}


