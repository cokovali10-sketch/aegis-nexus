package com.aegis.nexus.core.event;

import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    private final org.springframework.context.ApplicationEventPublisher applicationEventPublisher;

    public EventPublisher(org.springframework.context.ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(String eventType, Object source, String payload, String correlationId) {
        applicationEventPublisher.publishEvent(new DomainEvent(source, eventType, payload, correlationId));
    }

    public void publish(String eventType, Object source, String payload) {
        applicationEventPublisher.publishEvent(new DomainEvent(source, eventType, payload, null));
    }
}
