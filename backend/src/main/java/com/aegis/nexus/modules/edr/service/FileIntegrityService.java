package com.aegis.nexus.modules.edr.service;

import com.aegis.nexus.modules.edr.dto.FileIntegrityResponse;
import com.aegis.nexus.modules.edr.model.FileIntegrityRecord;
import com.aegis.nexus.modules.edr.repository.FileIntegrityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class FileIntegrityService {

    private final FileIntegrityRepository fileIntegrityRepository;

    public FileIntegrityService(FileIntegrityRepository fileIntegrityRepository) {
        this.fileIntegrityRepository = fileIntegrityRepository;
    }

    public List<FileIntegrityResponse> findByEndpointId(String endpointId) {
        return fileIntegrityRepository.findByEndpointId(endpointId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<FileIntegrityResponse> findUnauthorized() {
        return fileIntegrityRepository.findByEndpointIdAndIsAuthorized(null, false).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public FileIntegrityResponse createRecord(String endpointId, String filePath, String sha256,
                                              long fileSize, String changeType) {
        FileIntegrityRecord record = new FileIntegrityRecord(endpointId, filePath, sha256, fileSize, changeType);
        record = fileIntegrityRepository.save(record);
        return toResponse(record);
    }

    @Transactional
    public FileIntegrityResponse markAuthorized(String id, boolean authorized) {
        FileIntegrityRecord record = fileIntegrityRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Record not found: " + id));
        record.setAuthorized(authorized);
        record = fileIntegrityRepository.save(record);
        return toResponse(record);
    }

    private FileIntegrityResponse toResponse(FileIntegrityRecord r) {
        return new FileIntegrityResponse(
            r.getId(),
            r.getEndpointId(),
            r.getFilePath(),
            r.getSha256(),
            r.getFileSize(),
            r.getChangeType(),
            r.getDetectedAt().toString(),
            r.isAuthorized()
        );
    }
}
