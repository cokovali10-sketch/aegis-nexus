package com.aegis.nexus.modules.edr.repository;

import com.aegis.nexus.modules.edr.model.Endpoint;
import com.aegis.nexus.modules.edr.model.EndpointStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EndpointRepository extends JpaRepository<Endpoint, String> {
    Optional<Endpoint> findByAgentId(String agentId);
    List<Endpoint> findByStatus(EndpointStatus status);
    boolean existsByAgentId(String agentId);
}
