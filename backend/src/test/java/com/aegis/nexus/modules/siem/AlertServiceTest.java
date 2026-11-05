package com.aegis.nexus.modules.siem;

import com.aegis.nexus.modules.siem.dto.AlertResponse;
import com.aegis.nexus.modules.siem.model.Alert;
import com.aegis.nexus.modules.siem.model.AlertStatus;
import com.aegis.nexus.modules.siem.model.Severity;
import com.aegis.nexus.modules.siem.repository.AlertRepository;
import com.aegis.nexus.modules.siem.service.AlertService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {

    @Mock
    private AlertRepository alertRepository;

    @InjectMocks
    private AlertService alertService;

    @Test
    void createAlertSucceeds() {
        when(alertRepository.save(any(Alert.class))).thenAnswer(invocation -> {
            Alert a = invocation.getArgument(0);
            a.setId("alert-1");
            return a;
        });

        var response = alertService.createAlert("Test Alert", "Description", Severity.HIGH, "SIEM", "evt-1", "Rule1", "WKS-01");

        assertThat(response.title()).isEqualTo("Test Alert");
        assertThat(response.severity()).isEqualTo("HIGH");
        assertThat(response.status()).isEqualTo("NEW");
    }

    @Test
    void acknowledgeAlertWorks() {
        Alert alert = new Alert("Test", "Desc", Severity.HIGH, "SIEM", "evt-1", "Rule1", "WKS-01");
        alert.setId("alert-1");

        when(alertRepository.findById("alert-1")).thenReturn(Optional.of(alert));
        when(alertRepository.save(any(Alert.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = alertService.acknowledge("alert-1");
        assertThat(response.status()).isEqualTo("ACKNOWLEDGED");
    }

    @Test
    void resolveAlertWorks() {
        Alert alert = new Alert("Test", "Desc", Severity.HIGH, "SIEM", "evt-1", "Rule1", "WKS-01");
        alert.setId("alert-1");

        when(alertRepository.findById("alert-1")).thenReturn(Optional.of(alert));
        when(alertRepository.save(any(Alert.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = alertService.resolve("alert-1");
        assertThat(response.status()).isEqualTo("RESOLVED");
    }

    @Test
    void markFalsePositiveWorks() {
        Alert alert = new Alert("Test", "Desc", Severity.HIGH, "SIEM", "evt-1", "Rule1", "WKS-01");
        alert.setId("alert-1");

        when(alertRepository.findById("alert-1")).thenReturn(Optional.of(alert));
        when(alertRepository.save(any(Alert.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = alertService.markFalsePositive("alert-1");
        assertThat(response.status()).isEqualTo("FALSE_POSITIVE");
    }

    @Test
    void countByStatusWorks() {
        when(alertRepository.findByStatus(AlertStatus.NEW)).thenReturn(List.of());
        long count = alertService.countByStatus(AlertStatus.NEW);
        assertThat(count).isEqualTo(0);
    }
}
