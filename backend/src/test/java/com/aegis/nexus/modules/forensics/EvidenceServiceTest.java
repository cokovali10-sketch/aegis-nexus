package com.aegis.nexus.modules.forensics;

import com.aegis.nexus.modules.forensics.dto.EvidenceCreateRequest;
import com.aegis.nexus.modules.forensics.model.Evidence;
import com.aegis.nexus.modules.forensics.repository.EvidenceRepository;
import com.aegis.nexus.modules.forensics.service.EvidenceService;
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
class EvidenceServiceTest {
    @Mock private EvidenceRepository repo;
    @InjectMocks private EvidenceService service;

    @Test
    void createEvidenceSucceeds() {
        when(repo.save(any(Evidence.class))).thenAnswer(i -> { Evidence e = i.getArgument(0); e.setId("e1"); return e; });
        var r = service.createEvidence(new EvidenceCreateRequest("case-1", "kernel32.dll", "C:\\Windows\\System32\\kernel32.dll", "abc123", 1024000, "DLL", "metadata", "System file", "analyst1"));
        assertThat(r.fileName()).isEqualTo("kernel32.dll");
        assertThat(r.caseId()).isEqualTo("case-1");
    }

    @Test
    void verifyIntegrityWorks() {
        Evidence e = new Evidence(); e.setId("e1");
        when(repo.findById("e1")).thenReturn(Optional.of(e));
        when(repo.save(any(Evidence.class))).thenAnswer(i -> i.getArgument(0));
        var r = service.verifyIntegrity("e1");
        assertThat(r.integrityVerified()).isTrue();
    }

    @Test
    void findByCaseIdReturnsEmpty() {
        when(repo.findByCaseId("case-999")).thenReturn(List.of());
        assertThat(service.findByCaseId("case-999")).isEmpty();
    }
}
