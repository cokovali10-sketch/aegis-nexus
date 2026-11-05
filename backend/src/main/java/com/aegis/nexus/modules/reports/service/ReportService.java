package com.aegis.nexus.modules.reports.service;

import com.aegis.nexus.modules.siem.service.EventService;
import com.aegis.nexus.modules.siem.service.AlertService;
import com.aegis.nexus.modules.edr.service.EndpointService;
import com.aegis.nexus.modules.assets.service.AssetService;
import com.aegis.nexus.modules.vuln.service.VulnerabilityService;
import com.aegis.nexus.modules.response.service.IncidentService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    private final EventService eventService;
    private final AlertService alertService;
    private final EndpointService endpointService;
    private final AssetService assetService;
    private final VulnerabilityService vulnService;
    private final IncidentService incidentService;

    public ReportService(EventService eventService, AlertService alertService,
                        EndpointService endpointService, AssetService assetService,
                        VulnerabilityService vulnService, IncidentService incidentService) {
        this.eventService = eventService;
        this.alertService = alertService;
        this.endpointService = endpointService;
        this.assetService = assetService;
        this.vulnService = vulnService;
        this.incidentService = incidentService;
    }

    public Map<String, Object> generateExecutiveSummary() {
        Map<String, Object> report = new HashMap<>();
        report.put("report_type", "executive_summary");
        report.put("generated_at", java.time.Instant.now().toString());

        Map<String, Object> metrics = new HashMap<>();
        metrics.put("total_events", eventService.findAll().size());
        metrics.put("total_alerts", alertService.findAll().size());
        metrics.put("new_alerts", alertService.countByStatus(com.aegis.nexus.modules.siem.model.AlertStatus.NEW));
        metrics.put("total_endpoints", endpointService.findAllEndpoints().size());
        metrics.put("total_assets", assetService.findAll().size());
        metrics.put("unpatched_vulnerabilities", vulnService.countUnpatched());
        metrics.put("open_incidents", incidentService.countByStatus(com.aegis.nexus.modules.response.model.IncidentStatus.OPEN));
        report.put("metrics", metrics);

        report.put("sections", new String[]{
            "Security Posture Overview",
            "Endpoint Health Summary",
            "Incident Response Status",
            "Vulnerability Management",
            "Risk Distribution",
            "Recommendations"
        });

        return report;
    }

    public Map<String, Object> generateTechnicalReport() {
        Map<String, Object> report = new HashMap<>();
        report.put("report_type", "technical_report");
        report.put("generated_at", java.time.Instant.now().toString());

        report.put("sections", new String[]{
            "Event Analysis",
            "Detection Engine Results",
            "Endpoint Telemetry",
            "Network Intelligence",
            "Threat Intelligence",
            "Forensic Evidence",
            "Risk Assessment"
        });

        return report;
    }
}


