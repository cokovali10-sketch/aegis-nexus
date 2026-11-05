package com.aegis.nexus.modules.ai;

import com.aegis.nexus.modules.ai.service.AiAnalystService;
import com.aegis.nexus.modules.siem.service.AlertService;
import com.aegis.nexus.modules.siem.service.EventService;
import com.aegis.nexus.modules.edr.service.EndpointService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AiAnalystServiceTest {
    @Mock private EventService eventService;
    @Mock private AlertService alertService;
    @Mock private EndpointService endpointService;

    @Test
    void analyzeEventReturnsStructuredResult() {
        var service = new AiAnalystService(eventService, alertService, endpointService);
        Map<String, Object> result = service.analyzeEvent("evt-1");
        assertThat(result.get("event_id")).isEqualTo("evt-1");
        assertThat(result.get("risk_assessment")).isEqualTo("medium");
        assertThat(result.get("recommendations")).isNotNull();
    }

    @Test
    void platformOverviewReturnsStats() {
        org.mockito.Mockito.when(eventService.findAll()).thenReturn(java.util.List.of());
        org.mockito.Mockito.when(alertService.findAll()).thenReturn(java.util.List.of());
        org.mockito.Mockito.when(endpointService.findAllEndpoints()).thenReturn(java.util.List.of());

        var service = new AiAnalystService(eventService, alertService, endpointService);
        Map<String, Object> overview = service.getPlatformOverview();
        assertThat(overview.get("ai_status")).isEqualTo("ready");
        assertThat(overview.get("total_events")).isEqualTo(0);
    }

    @Test
    void suggestStepsReturnsSteps() {
        var service = new AiAnalystService(eventService, alertService, endpointService);
        Map<String, Object> result = service.suggestInvestigationSteps("suspicious process");
        assertThat(result.get("steps")).isNotNull();
        assertThat(result.get("priority")).isEqualTo("high");
    }
}
