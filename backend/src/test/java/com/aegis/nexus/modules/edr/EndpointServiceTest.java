package com.aegis.nexus.modules.edr;

import com.aegis.nexus.modules.edr.dto.EndpointRegisterRequest;
import com.aegis.nexus.modules.edr.model.Endpoint;
import com.aegis.nexus.modules.edr.model.EndpointStatus;
import com.aegis.nexus.modules.edr.repository.EndpointRepository;
import com.aegis.nexus.modules.edr.service.EndpointService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EndpointServiceTest {

    @Mock
    private EndpointRepository endpointRepository;

    @InjectMocks
    private EndpointService endpointService;

    @Test
    void registerEndpointSucceeds() {
        EndpointRegisterRequest request = new EndpointRegisterRequest(
            "agent-1", "WKS-01", "10.0.1.10", "Windows 11", "23H2", "1.0.0"
        );
        when(endpointRepository.existsByAgentId("agent-1")).thenReturn(false);
        when(endpointRepository.save(any(Endpoint.class))).thenAnswer(invocation -> {
            Endpoint e = invocation.getArgument(0);
            e.setId("ep-1");
            e.setLastSeenAt(Instant.now());
            return e;
        });

        var response = endpointService.registerEndpoint(request);

        assertThat(response.hostname()).isEqualTo("WKS-01");
        assertThat(response.status()).isEqualTo("ONLINE");
        assertThat(response.isIsolated()).isFalse();
    }

    @Test
    void registerDuplicateAgentThrows() {
        EndpointRegisterRequest request = new EndpointRegisterRequest(
            "agent-1", "WKS-01", "10.0.1.10", "Windows 11", "23H2", "1.0.0"
        );
        when(endpointRepository.existsByAgentId("agent-1")).thenReturn(true);

        assertThat(org.assertj.core.api.Assertions.catchThrowable(() ->
            endpointService.registerEndpoint(request)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void heartbeatUpdatesLastSeen() {
        Endpoint endpoint = new Endpoint();
        endpoint.setId("ep-1");
        endpoint.setAgentId("agent-1");
        endpoint.setStatus(EndpointStatus.OFFLINE);

        when(endpointRepository.findByAgentId("agent-1")).thenReturn(Optional.of(endpoint));
        when(endpointRepository.save(any(Endpoint.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = endpointService.heartbeat("agent-1");

        assertThat(response.status()).isEqualTo("ONLINE");
        assertThat(response.lastSeenAt()).isNotNull();
    }

    @Test
    void isolateEndpointWorks() {
        Endpoint endpoint = new Endpoint();
        endpoint.setId("ep-1");
        endpoint.setAgentId("agent-1");

        when(endpointRepository.findById("ep-1")).thenReturn(Optional.of(endpoint));
        when(endpointRepository.save(any(Endpoint.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = endpointService.isolateEndpoint("ep-1", true);

        assertThat(response.isIsolated()).isTrue();
        assertThat(response.status()).isEqualTo("ISOLATED");
    }

    @Test
    void isolateNotFoundThrows() {
        when(endpointRepository.findById("ep-999")).thenReturn(Optional.empty());
        assertThat(org.assertj.core.api.Assertions.catchThrowable(() ->
            endpointService.isolateEndpoint("ep-999", true)))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
