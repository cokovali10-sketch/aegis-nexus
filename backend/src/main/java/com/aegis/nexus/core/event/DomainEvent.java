package com.aegis.nexus.core.event;

import org.springframework.context.ApplicationEvent;

public class DomainEvent extends ApplicationEvent {

    private final String eventType;
    private final String source;
    private final String correlationId;

    public DomainEvent(Object source, String eventType, String payload, String correlationId) {
        super(source);
        this.eventType = eventType;
        this.source = source.getClass().getSimpleName();
        this.correlationId = correlationId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getSource() {
        return source;
    }

    public String getCorrelationId() {
        return correlationId;
    }
}
