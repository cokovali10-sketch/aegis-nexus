package com.aegis.nexus.core.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class EventLogger implements ApplicationListener<DomainEvent> {

    @Override
    public void onApplicationEvent(DomainEvent event) {
        System.out.printf("[EVENT] type=%s source=%s correlationId=%s%n",
            event.getEventType(), event.getSource(), event.getCorrelationId());
    }
}
