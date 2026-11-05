package com.aegis.nexus.modules.automation;

import com.aegis.nexus.modules.automation.dto.WorkflowCreateRequest;
import com.aegis.nexus.modules.automation.model.Workflow;
import com.aegis.nexus.modules.automation.repository.WorkflowExecutionRepository;
import com.aegis.nexus.modules.automation.repository.WorkflowRepository;
import com.aegis.nexus.modules.automation.service.WorkflowService;
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
class WorkflowServiceTest {
    @Mock private WorkflowRepository repo;
    @Mock private WorkflowExecutionRepository execRepo;
    @InjectMocks private WorkflowService service;

    @Test
    void createWorkflowSucceeds() {
        when(repo.save(any(Workflow.class))).thenAnswer(i -> { Workflow w = i.getArgument(0); w.setId("w1"); return w; });
        var r = service.createWorkflow(new WorkflowCreateRequest("Test Workflow", "Desc", "EVENT", "{}", "{}", "[]", true));
        assertThat(r.name()).isEqualTo("Test Workflow");
        assertThat(r.isEnabled()).isTrue();
    }

    @Test
    void toggleEnabledWorks() {
        Workflow w = new Workflow(); w.setId("w1"); w.setEnabled(true);
        when(repo.findById("w1")).thenReturn(Optional.of(w));
        when(repo.save(any(Workflow.class))).thenAnswer(i -> i.getArgument(0));
        var r = service.toggleEnabled("w1", false);
        assertThat(r.isEnabled()).isFalse();
    }

    @Test
    void findEnabledReturnsEmpty() {
        when(repo.findByIsEnabled(true)).thenReturn(List.of());
        assertThat(service.findEnabled()).isEmpty();
    }
}
