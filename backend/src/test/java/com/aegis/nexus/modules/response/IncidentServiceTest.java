package com.aegis.nexus.modules.response;

import com.aegis.nexus.modules.response.dto.IncidentCreateRequest;
import com.aegis.nexus.modules.response.model.Incident;
import com.aegis.nexus.modules.response.model.IncidentStatus;
import com.aegis.nexus.modules.response.model.Severity;
import com.aegis.nexus.modules.response.repository.IncidentRepository;
import com.aegis.nexus.modules.response.service.IncidentService;
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
class IncidentServiceTest {
    @Mock private IncidentRepository repo;
    @InjectMocks private IncidentService service;

    @Test
    void createIncidentSucceeds() {
        when(repo.save(any(Incident.class))).thenAnswer(i -> { Incident inc = i.getArgument(0); inc.setId("inc-1"); return inc; });
        var r = service.createIncident(new IncidentCreateRequest("Test Incident", "Description", "HIGH", "WKS-01", "analyst1", "Initial notes"));
        assertThat(r.title()).isEqualTo("Test Incident");
        assertThat(r.severity()).isEqualTo("HIGH");
        assertThat(r.status()).isEqualTo("OPEN");
    }

    @Test
    void createIncidentWithInvalidSeverityDefaultsToMedium() {
        when(repo.save(any(Incident.class))).thenAnswer(i -> { Incident inc = i.getArgument(0); inc.setId("inc-2"); return inc; });
        var r = service.createIncident(new IncidentCreateRequest("Test", "Desc", "INVALID", "host", null, null));
        assertThat(r.severity()).isEqualTo("MEDIUM");
    }

    @Test
    void updateStatusToResolvedSetsResolvedAt() {
        Incident inc = new Incident(); inc.setId("inc-1"); inc.setSeverity(Severity.HIGH); inc.setStatus(IncidentStatus.OPEN);
        when(repo.findById("inc-1")).thenReturn(Optional.of(inc));
        when(repo.save(any(Incident.class))).thenAnswer(i -> i.getArgument(0));
        var r = service.updateStatus("inc-1", IncidentStatus.RESOLVED);
        assertThat(r.status()).isEqualTo("RESOLVED");
        assertThat(r.resolvedAt()).isNotNull();
    }

    @Test
    void assignIncidentWorks() {
        Incident inc = new Incident(); inc.setId("inc-1"); inc.setSeverity(Severity.HIGH);
        when(repo.findById("inc-1")).thenReturn(Optional.of(inc));
        when(repo.save(any(Incident.class))).thenAnswer(i -> i.getArgument(0));
        var r = service.assign("inc-1", "analyst2");
        assertThat(r.assignee()).isEqualTo("analyst2");
    }

    @Test
    void addNotesAppendsToExisting() {
        Incident inc = new Incident(); inc.setId("inc-1"); inc.setSeverity(Severity.HIGH); inc.setNotes("Note 1");
        when(repo.findById("inc-1")).thenReturn(Optional.of(inc));
        when(repo.save(any(Incident.class))).thenAnswer(i -> i.getArgument(0));
        var r = service.addNotes("inc-1", "Note 2");
        assertThat(r.notes()).contains("Note 1").contains("Note 2");
    }

    @Test
    void countByStatusWorks() {
        when(repo.findByStatus(IncidentStatus.OPEN)).thenReturn(List.of());
        assertThat(service.countByStatus(IncidentStatus.OPEN)).isEqualTo(0);
    }

    @Test
    void findByStatusReturnsEmpty() {
        when(repo.findByStatus(IncidentStatus.CLOSED)).thenReturn(List.of());
        assertThat(service.findByStatus(IncidentStatus.CLOSED)).isEmpty();
    }
}
