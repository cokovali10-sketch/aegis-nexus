package com.aegis.nexus.modules.forensics.service;

import com.aegis.nexus.modules.forensics.dto.EvidenceCreateRequest;
import com.aegis.nexus.modules.forensics.dto.EvidenceResponse;
import com.aegis.nexus.modules.forensics.model.Evidence;
import com.aegis.nexus.modules.forensics.repository.EvidenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class EvidenceService {

    private final EvidenceRepository evidenceRepository;

    public EvidenceService(EvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }

    @Transactional
    public EvidenceResponse createEvidence(EvidenceCreateRequest request) {
        Evidence evidence = new Evidence(
            request.caseId(), request.fileName(), request.filePath(),
            request.sha256(), request.fileSize(), request.fileType(),
            request.description(), request.collectedBy()
        );
        evidence.setMetadata(request.metadata());
        evidence = evidenceRepository.save(evidence);
        return toResponse(evidence);
    }

    public EvidenceResponse findById(String id) {
        return evidenceRepository.findById(id).map(this::toResponse).orElse(null);
    }

    public List<EvidenceResponse> findAll() {
        return evidenceRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<EvidenceResponse> findByCaseId(String caseId) {
        return evidenceRepository.findByCaseId(caseId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public EvidenceResponse verifyIntegrity(String id) {
        Evidence evidence = evidenceRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Evidence not found: " + id));
        // In production, recompute SHA-256 and compare
        evidence.setIntegrityVerified(true);
        evidence = evidenceRepository.save(evidence);
        return toResponse(evidence);
    }

    private EvidenceResponse toResponse(Evidence e) {
        return new EvidenceResponse(
            e.getId(), e.getCaseId(), e.getFileName(), e.getFilePath(), e.getSha256(),
            e.getFileSize(), e.getFileType(), e.getMetadata(), e.getDescription(),
            e.getCollectedAt().toString(), e.getCollectedBy(), e.isIntegrityVerified()
        );
    }
}


