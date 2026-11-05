package com.aegis.nexus.modules.automation.repository;

import com.aegis.nexus.modules.automation.model.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, String> {
    List<Workflow> findByIsEnabled(boolean enabled);
    List<Workflow> findByTriggerType(String triggerType);
}
