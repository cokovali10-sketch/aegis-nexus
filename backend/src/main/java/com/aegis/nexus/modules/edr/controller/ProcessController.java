package com.aegis.nexus.modules.edr.controller;

import com.aegis.nexus.modules.edr.dto.ProcessResponse;
import com.aegis.nexus.modules.edr.service.ProcessService;
import com.aegis.nexus.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/edr/processes")
@PreAuthorize("hasRole('ROLE_ANALYST') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
public class ProcessController {

    private final ProcessService processService;

    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    @GetMapping("/endpoint/{endpointId}")
    public ResponseEntity<ApiResponse<List<ProcessResponse>>> listByEndpoint(@PathVariable String endpointId) {
        List<ProcessResponse> processes = processService.findByEndpointId(endpointId);
        return ResponseEntity.ok(ApiResponse.ok(processes));
    }

    @GetMapping("/endpoint/{endpointId}/suspicious")
    public ResponseEntity<ApiResponse<List<ProcessResponse>>> listSuspicious(@PathVariable String endpointId) {
        List<ProcessResponse> processes = processService.findSuspiciousByEndpointId(endpointId);
        return ResponseEntity.ok(ApiResponse.ok(processes));
    }

    @PostMapping("/endpoint/{endpointId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<ProcessResponse>> createProcess(@PathVariable String endpointId,
                                                                       @RequestParam int pid,
                                                                       @RequestParam String name,
                                                                       @RequestParam String executablePath,
                                                                       @RequestParam String commandLine,
                                                                       @RequestParam(required = false) Integer parentPid,
                                                                       @RequestParam String user) {
        ProcessResponse response = processService.createProcess(endpointId, pid, name, executablePath,
            commandLine, parentPid, user);
        return ResponseEntity.ok(ApiResponse.ok(response, "Process recorded"));
    }

    @PostMapping("/{id}/suspicious")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<ProcessResponse>> markSuspicious(@PathVariable String id,
                                                                        @RequestParam boolean suspicious) {
        ProcessResponse response = processService.markSuspicious(id, suspicious);
        return ResponseEntity.ok(ApiResponse.ok(response, "Process suspicious flag updated"));
    }
}


