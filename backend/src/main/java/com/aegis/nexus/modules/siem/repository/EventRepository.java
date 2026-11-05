package com.aegis.nexus.modules.siem.repository;

import com.aegis.nexus.modules.siem.model.SecurityEvent;
import com.aegis.nexus.modules.siem.model.EventType;
import com.aegis.nexus.modules.siem.model.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<SecurityEvent, String> {
    List<SecurityEvent> findByEventType(EventType eventType);
    List<SecurityEvent> findBySeverity(Severity severity);
    List<SecurityEvent> findByHostname(String hostname);
    List<SecurityEvent> findByTimestampBetween(Instant start, Instant end);
    List<SecurityEvent> findByIsAlert(boolean isAlert);

    @Query("SELECT e FROM SecurityEvent e WHERE e.timestamp >= :start ORDER BY e.timestamp DESC")
    List<SecurityEvent> findRecentEvents(Instant start);
}


