package com.aegis.nexus.core.event;

import java.time.Instant;
import java.util.UUID;

public record PlatformEvent(
    String id,
    Instant timestamp,
    String type,
    String source,
    String payload,
    String correlationId
) {
    public PlatformEvent(String type, String source, String payload, String correlationId) {
        this(UUID.randomUUID().toString(), Instant.now(), type, source, payload, correlationId);
    }

    public PlatformEvent(String type, String source, String payload) {
        this(type, source, payload, null);
    }
}
