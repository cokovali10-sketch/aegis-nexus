package com.aegis.nexus.modules.edr.repository;

import com.aegis.nexus.modules.edr.model.FileIntegrityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileIntegrityRepository extends JpaRepository<FileIntegrityRecord, String> {
    List<FileIntegrityRecord> findByEndpointId(String endpointId);
    List<FileIntegrityRecord> findByEndpointIdAndIsAuthorized(String endpointId, boolean authorized);
}
