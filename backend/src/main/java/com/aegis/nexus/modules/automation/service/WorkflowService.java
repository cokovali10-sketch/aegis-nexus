package com.aegis.nexus.modules.automation.service;

import com.aegis.nexus.modules.automation.dto.WorkflowCreateRequest;
import com.aegis.nexus.modules.automation.dto.WorkflowResponse;
import com.aegis.nexus.modules.automation.model.Workflow;
import com.aegis.nexus.modules.automation.model.WorkflowExecution;
import com.aegis.nexus.modules.automation.repository.WorkflowExecutionRepository;
import com.aegis.nexus.modules.automation.repository.WorkflowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class WorkflowService {

    private final WorkflowRepository workflowRepository;
    private final WorkflowExecutionRepository executionRepository;

    public WorkflowService(WorkflowRepository workflowRepository, WorkflowExecutionRepository executionRepository) {
        this.workflowRepository = workflowRepository;
        this.executionRepository = executionRepository;
    }

    @Transactional
    public WorkflowResponse createWorkflow(WorkflowCreateRequest request) {
        Workflow workflow = new Workflow(
            request.name(), request.description(), request.triggerType(),
            request.triggerConfig(), request.conditions(), request.actions()
        );
        workflow.setEnabled(request.isEnabled() != null ? request.isEnabled() : true);
        workflow = workflowRepository.save(workflow);
        return toResponse(workflow);
    }

    public WorkflowResponse findById(String id) {
        return workflowRepository.findById(id).map(this::toResponse).orElse(null);
    }

    public List<WorkflowResponse> findAll() {
        return workflowRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<WorkflowResponse> findEnabled() {
        return workflowRepository.findByIsEnabled(true).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<WorkflowResponse> findByTriggerType(String triggerType) {
        return workflowRepository.findByTriggerType(triggerType).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public WorkflowResponse toggleEnabled(String id, boolean enabled) {
        Workflow workflow = workflowRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Workflow not found: " + id));
        workflow.setEnabled(enabled);
        workflow.setUpdatedAt(Instant.now());
        workflow = workflowRepository.save(workflow);
        return toResponse(workflow);
    }

    @Transactional
    public WorkflowResponse recordExecution(String id, boolean success, String result, String errorMessage) {
        Workflow workflow = workflowRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Workflow not found: " + id));
        workflow.setExecutionCount(workflow.getExecutionCount() + 1);
        workflow.setLastExecutedAt(Instant.now());
        workflow.setUpdatedAt(Instant.now());
        workflow = workflowRepository.save(workflow);

        WorkflowExecution execution = new WorkflowExecution(id, success ? "SUCCESS" : "FAILED");
        execution.setResult(result);
        execution.setErrorMessage(errorMessage);
        execution.setCompletedAt(Instant.now());
        executionRepository.save(execution);

        return toResponse(workflow);
    }

    public List<WorkflowExecution> getExecutionHistory(String workflowId) {
        return executionRepository.findByWorkflowId(workflowId);
    }

    private WorkflowResponse toResponse(Workflow w) {
        return new WorkflowResponse(
            w.getId(), w.getName(), w.getDescription(), w.getTriggerType(),
            w.getTriggerConfig(), w.getConditions(), w.getActions(),
            w.isEnabled(), w.getExecutionCount(),
            w.getLastExecutedAt() != null ? w.getLastExecutedAt().toString() : null,
            w.getCreatedAt().toString(),
            w.getUpdatedAt() != null ? w.getUpdatedAt().toString() : null
        );
    }
}


