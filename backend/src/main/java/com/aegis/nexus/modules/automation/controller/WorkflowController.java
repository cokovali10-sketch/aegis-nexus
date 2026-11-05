package com.aegis.nexus.modules.automation.controller;

import com.aegis.nexus.modules.automation.dto.WorkflowCreateRequest;
import com.aegis.nexus.modules.automation.dto.WorkflowResponse;
import com.aegis.nexus.modules.automation.model.Workflow;
import com.aegis.nexus.modules.automation.model.WorkflowExecution;
import com.aegis.nexus.modules.automation.service.WorkflowService;
import com.aegis.nexus.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/automation")
@PreAuthorize("hasRole('ROLE_ANALYST') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @PostMapping("/workflows")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<WorkflowResponse>> createWorkflow(@RequestBody WorkflowCreateRequest request) {
        WorkflowResponse response = workflowService.createWorkflow(request);
        return ResponseEntity.ok(ApiResponse.ok(response, "Workflow created"));
    }

    @GetMapping("/workflows")
    public ResponseEntity<ApiResponse<List<WorkflowResponse>>> listWorkflows() {
        List<WorkflowResponse> workflows = workflowService.findAll();
        return ResponseEntity.ok(ApiResponse.ok(workflows));
    }

    @GetMapping("/workflows/enabled")
    public ResponseEntity<ApiResponse<List<WorkflowResponse>>> listEnabled() {
        List<WorkflowResponse> workflows = workflowService.findEnabled();
        return ResponseEntity.ok(ApiResponse.ok(workflows));
    }

    @GetMapping("/workflows/{id}")
    public ResponseEntity<ApiResponse<WorkflowResponse>> getWorkflow(@PathVariable String id) {
        WorkflowResponse response = workflowService.findById(id);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PostMapping("/workflows/{id}/toggle")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<WorkflowResponse>> toggleWorkflow(@PathVariable String id, @RequestParam boolean enabled) {
        WorkflowResponse response = workflowService.toggleEnabled(id, enabled);
        return ResponseEntity.ok(ApiResponse.ok(response, "Workflow toggled"));
    }

    @PostMapping("/workflows/{id}/execute")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<WorkflowResponse>> executeWorkflow(@PathVariable String id) {
        WorkflowResponse response = workflowService.recordExecution(id, true, "Manual execution triggered", null);
        return ResponseEntity.ok(ApiResponse.ok(response, "Workflow executed"));
    }

    @GetMapping("/workflows/{id}/history")
    public ResponseEntity<ApiResponse<List<WorkflowExecution>>> getHistory(@PathVariable String id) {
        List<WorkflowExecution> history = workflowService.getExecutionHistory(id);
        return ResponseEntity.ok(ApiResponse.ok(history));
    }
}


