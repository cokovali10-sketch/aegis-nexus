package com.aegis.nexus.modules.forensics.repository;

import com.aegis.nexus.modules.forensics.model.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, String> {
    List<Evidence> findByCaseId(String caseId);
    List<Evidence> findByCollectedBy(String collectedBy);
}


