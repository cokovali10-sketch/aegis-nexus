package com.aegis.nexus.modules.edr.service;

import com.aegis.nexus.modules.edr.dto.EndpointRegisterRequest;
import com.aegis.nexus.modules.edr.dto.EndpointResponse;
import com.aegis.nexus.modules.edr.model.Endpoint;
import com.aegis.nexus.modules.edr.model.EndpointStatus;
import com.aegis.nexus.modules.edr.repository.EndpointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class EndpointService {

    private final EndpointRepository endpointRepository;

    public EndpointService(EndpointRepository endpointRepository) {
        this.endpointRepository = endpointRepository;
    }

    @Transactional
    public EndpointResponse registerEndpoint(EndpointRegisterRequest request) {
        if (endpointRepository.existsByAgentId(request.agentId())) {
            throw new IllegalArgumentException("Agent already registered: " + request.agentId());
        }

        Endpoint endpoint = new Endpoint(
            request.agentId(),
            request.hostname(),
            request.ipAddress(),
            request.os(),
            request.osVersion(),
            request.agentVersion()
        );
        endpoint.setStatus(EndpointStatus.ONLINE);
        endpoint.setLastSeenAt(Instant.now());
        endpoint = endpointRepository.save(endpoint);
        return toResponse(endpoint);
    }

    public Optional<EndpointResponse> findByAgentId(String agentId) {
        return endpointRepository.findByAgentId(agentId).map(this::toResponse);
    }

    public List<EndpointResponse> findAllEndpoints() {
        return endpointRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<EndpointResponse> findByStatus(EndpointStatus status) {
        return endpointRepository.findByStatus(status).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public EndpointResponse heartbeat(String agentId) {
        Optional<Endpoint> optional = endpointRepository.findByAgentId(agentId);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Endpoint not found: " + agentId);
        }
        Endpoint endpoint = optional.get();
        endpoint.setLastSeenAt(Instant.now());
        endpoint.setStatus(EndpointStatus.ONLINE);
        endpoint = endpointRepository.save(endpoint);
        return toResponse(endpoint);
    }

    @Transactional
    public EndpointResponse isolateEndpoint(String id, boolean isolate) {
        Endpoint endpoint = endpointRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Endpoint not found: " + id));
        endpoint.setIsolated(isolate);
        endpoint.setStatus(isolate ? EndpointStatus.ISOLATED : EndpointStatus.ONLINE);
        endpoint = endpointRepository.save(endpoint);
        return toResponse(endpoint);
    }

    private EndpointResponse toResponse(Endpoint e) {
        return new EndpointResponse(
            e.getId(),
            e.getAgentId(),
            e.getHostname(),
            e.getIpAddress(),
            e.getOs(),
            e.getOsVersion(),
            e.getAgentVersion(),
            e.getStatus().name(),
            e.getLastSeenAt() != null ? e.getLastSeenAt().toString() : null,
            e.getRiskScore(),
            e.isIsolated()
        );
    }
}


