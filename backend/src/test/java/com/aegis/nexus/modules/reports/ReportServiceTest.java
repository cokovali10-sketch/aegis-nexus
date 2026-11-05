package com.aegis.nexus.modules.reports;

import com.aegis.nexus.modules.reports.service.ReportService;
import com.aegis.nexus.modules.siem.service.AlertService;
import com.aegis.nexus.modules.siem.service.EventService;
import com.aegis.nexus.modules.edr.service.EndpointService;
import com.aegis.nexus.modules.assets.service.AssetService;
import com.aegis.nexus.modules.vuln.service.VulnerabilityService;
import com.aegis.nexus.modules.response.service.IncidentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {
    @Mock private EventService eventService;
    @Mock private AlertService alertService;
    @Mock private EndpointService endpointService;
    @Mock private AssetService assetService;
    @Mock private VulnerabilityService vulnService;
    @Mock private IncidentService incidentService;

    @Test
    void executiveSummaryHasMetrics() {
        when(eventService.findAll()).thenReturn(java.util.List.of());
        when(alertService.findAll()).thenReturn(java.util.List.of());
        when(alertService.countByStatus(com.aegis.nexus.modules.siem.model.AlertStatus.NEW)).thenReturn(0L);
        when(endpointService.findAllEndpoints()).thenReturn(java.util.List.of());
        when(assetService.findAll()).thenReturn(java.util.List.of());
        when(vulnService.countUnpatched()).thenReturn(0L);
        when(incidentService.countByStatus(com.aegis.nexus.modules.response.model.IncidentStatus.OPEN)).thenReturn(0L);

        var service = new ReportService(eventService, alertService, endpointService, assetService, vulnService, incidentService);
        Map<String, Object> report = service.generateExecutiveSummary();
        assertThat(report.get("report_type")).isEqualTo("executive_summary");
        assertThat(report.get("metrics")).isNotNull();
        assertThat(report.get("sections")).isNotNull();
    }

    @Test
    void technicalReportHasSections() {
        var service = new ReportService(eventService, alertService, endpointService, assetService, vulnService, incidentService);
        Map<String, Object> report = service.generateTechnicalReport();
        assertThat(report.get("report_type")).isEqualTo("technical_report");
        assertThat(report.get("sections")).isNotNull();
    }
}
