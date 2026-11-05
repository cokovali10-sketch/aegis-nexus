package com.aegis.nexus.modules.siem.repository;

import com.aegis.nexus.modules.siem.model.Alert;
import com.aegis.nexus.modules.siem.model.AlertStatus;
import com.aegis.nexus.modules.siem.model.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, String> {
    List<Alert> findByStatus(AlertStatus status);
    List<Alert> findBySeverity(Severity severity);
    List<Alert> findByHostname(String hostname);
    List<Alert> findByAssignee(String assignee);
}
