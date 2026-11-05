package com.aegis.nexus.modules.edr.controller;

import com.aegis.nexus.modules.edr.dto.EndpointRegisterRequest;
import com.aegis.nexus.modules.edr.dto.EndpointResponse;
import com.aegis.nexus.modules.edr.service.EndpointService;
import com.aegis.nexus.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/edr/endpoints")
@PreAuthorize("hasRole('ROLE_ANALYST') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
public class EndpointController {

    private final EndpointService endpointService;

    public EndpointController(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<EndpointResponse>> register(@RequestBody EndpointRegisterRequest request) {
        EndpointResponse response = endpointService.registerEndpoint(request);
        return ResponseEntity.ok(ApiResponse.ok(response, "Endpoint registered successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EndpointResponse>>> listAll() {
        List<EndpointResponse> endpoints = endpointService.findAllEndpoints();
        return ResponseEntity.ok(ApiResponse.ok(endpoints));
    }

    @GetMapping("/{agentId}")
    public ResponseEntity<ApiResponse<EndpointResponse>> getByAgentId(@PathVariable String agentId) {
        return endpointService.findByAgentId(agentId)
            .map(e -> ResponseEntity.ok(ApiResponse.ok(e)))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<EndpointResponse>>> getByStatus(@PathVariable String status) {
        List<EndpointResponse> endpoints = endpointService.findByStatus(
            com.aegis.nexus.modules.edr.model.EndpointStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(ApiResponse.ok(endpoints));
    }

    @PostMapping("/{agentId}/heartbeat")
    public ResponseEntity<ApiResponse<EndpointResponse>> heartbeat(@PathVariable String agentId) {
        EndpointResponse response = endpointService.heartbeat(agentId);
        return ResponseEntity.ok(ApiResponse.ok(response, "Heartbeat received"));
    }

    @PostMapping("/{id}/isolate")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<EndpointResponse>> isolate(@PathVariable String id,
                                                                  @RequestParam boolean isolate) {
        EndpointResponse response = endpointService.isolateEndpoint(id, isolate);
        return ResponseEntity.ok(ApiResponse.ok(response, "Endpoint isolation updated"));
    }
}
