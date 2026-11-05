package com.aegis.nexus.modules.siem.service;

import com.aegis.nexus.modules.siem.dto.EventCreateRequest;
import com.aegis.nexus.modules.siem.dto.EventResponse;
import com.aegis.nexus.modules.siem.model.EventType;
import com.aegis.nexus.modules.siem.model.SecurityEvent;
import com.aegis.nexus.modules.siem.model.Severity;
import com.aegis.nexus.modules.siem.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public EventResponse createEvent(EventCreateRequest request) {
        EventType eventType;
        try {
            eventType = EventType.valueOf(request.eventType().toUpperCase());
        } catch (IllegalArgumentException e) {
            eventType = EventType.CUSTOM;
        }

        Severity severity = Severity.INFO;
        if (request.severity() != null) {
            try {
                severity = Severity.valueOf(request.severity().toUpperCase());
            } catch (IllegalArgumentException e) {
                severity = Severity.INFO;
            }
        }

        SecurityEvent event = new SecurityEvent(eventType, severity, request.source(), request.description());
        event.setHostname(request.hostname());
        event.setUsername(request.username());
        event.setIpAddress(request.ipAddress());
        event.setRawData(request.rawData());
        event = eventRepository.save(event);
        return toResponse(event);
    }

    public EventResponse findById(String id) {
        return eventRepository.findById(id).map(this::toResponse).orElse(null);
    }

    public List<EventResponse> findAll() {
        return eventRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<EventResponse> findByType(EventType type) {
        return eventRepository.findByEventType(type).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<EventResponse> findBySeverity(Severity severity) {
        return eventRepository.findBySeverity(severity).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<EventResponse> findRecent(int minutes) {
        Instant start = Instant.now().minusSeconds(minutes * 60L);
        return eventRepository.findRecentEvents(start).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public long countByType(EventType type) {
        return eventRepository.findByEventType(type).size();
    }

    public long countAlerts() {
        return eventRepository.findByIsAlert(true).size();
    }

    private EventResponse toResponse(SecurityEvent e) {
        return new EventResponse(
            e.getId(),
            e.getTimestamp().toString(),
            e.getEventType().name(),
            e.getSeverity().name(),
            e.getSource(),
            e.getHostname(),
            e.getUsername(),
            e.getIpAddress(),
            e.getDescription(),
            e.getCorrelationId(),
            e.isAlert()
        );
    }
}
