package com.aegis.nexus.modules.edr.repository;

import com.aegis.nexus.modules.edr.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<Process, String> {
    List<Process> findByEndpointId(String endpointId);
    List<Process> findByEndpointIdAndIsSuspicious(String endpointId, boolean suspicious);
}


