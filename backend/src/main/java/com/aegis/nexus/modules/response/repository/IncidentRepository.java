package com.aegis.nexus.modules.response.repository;

import com.aegis.nexus.modules.response.model.Incident;
import com.aegis.nexus.modules.response.model.IncidentStatus;
import com.aegis.nexus.modules.response.model.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, String> {
    List<Incident> findByStatus(IncidentStatus status);
    List<Incident> findBySeverity(Severity severity);
    List<Incident> findByHostname(String hostname);
    List<Incident> findByAssignee(String assignee);
}
