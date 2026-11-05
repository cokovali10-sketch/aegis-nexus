package com.aegis.nexus.modules.siem;

import com.aegis.nexus.modules.siem.dto.EventCreateRequest;
import com.aegis.nexus.modules.siem.model.SecurityEvent;
import com.aegis.nexus.modules.siem.model.EventType;
import com.aegis.nexus.modules.siem.model.Severity;
import com.aegis.nexus.modules.siem.repository.EventRepository;
import com.aegis.nexus.modules.siem.service.EventService;
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
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void createEventSucceeds() {
        when(eventRepository.save(any(SecurityEvent.class))).thenAnswer(invocation -> {
            SecurityEvent e = invocation.getArgument(0);
            e.setId("evt-1");
            return e;
        });

        var request = new EventCreateRequest("AUTH_FAILURE", "HIGH", "SIEM Collector", "WKS-01", "admin", "10.0.1.1", "Failed login attempt", null);
        var response = eventService.createEvent(request);

        assertThat(response.eventType()).isEqualTo("AUTH_FAILURE");
        assertThat(response.severity()).isEqualTo("HIGH");
        assertThat(response.hostname()).isEqualTo("WKS-01");
    }

    @Test
    void createEventWithInvalidTypeDefaultsToCustom() {
        when(eventRepository.save(any(SecurityEvent.class))).thenAnswer(invocation -> {
            SecurityEvent e = invocation.getArgument(0);
            e.setId("evt-2");
            return e;
        });

        var request = new EventCreateRequest("INVALID_TYPE", "INFO", "source", "host", "user", "ip", "desc", null);
        var response = eventService.createEvent(request);

        assertThat(response.eventType()).isEqualTo("CUSTOM");
    }

    @Test
    void findRecentReturnsEvents() {
        when(eventRepository.findRecentEvents(any())).thenReturn(List.of());
        var results = eventService.findRecent(60);
        assertThat(results).isEmpty();
    }

    @Test
    void countByTypeWorks() {
        when(eventRepository.findByEventType(EventType.AUTH_FAILURE)).thenReturn(List.of());
        long count = eventService.countByType(EventType.AUTH_FAILURE);
        assertThat(count).isEqualTo(0);
    }
}
