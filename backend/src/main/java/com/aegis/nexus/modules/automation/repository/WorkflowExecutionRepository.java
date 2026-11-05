package com.aegis.nexus.modules.automation.repository;

import com.aegis.nexus.modules.automation.model.WorkflowExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowExecutionRepository extends JpaRepository<WorkflowExecution, String> {
    List<WorkflowExecution> findByWorkflowId(String workflowId);
}
