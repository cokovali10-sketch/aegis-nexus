package com.aegis.nexus.modules.intel.service;

import com.aegis.nexus.modules.intel.dto.IocCreateRequest;
import com.aegis.nexus.modules.intel.dto.IocResponse;
import com.aegis.nexus.modules.intel.model.Ioc;
import com.aegis.nexus.modules.intel.model.IocType;
import com.aegis.nexus.modules.intel.model.ThreatType;
import com.aegis.nexus.modules.intel.repository.IocRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class IocService {

    private final IocRepository iocRepository;

    public IocService(IocRepository iocRepository) {
        this.iocRepository = iocRepository;
    }

    @Transactional
    public IocResponse createIoc(IocCreateRequest request) {
        IocType type;
        try {
            type = IocType.valueOf(request.type().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid IOC type: " + request.type());
        }

        if (iocRepository.existsByTypeAndValue(type, request.value())) {
            throw new IllegalArgumentException("IOC already exists: " + request.value());
        }

        ThreatType threatType = ThreatType.UNKNOWN;
        if (request.threatType() != null) {
            try {
                threatType = ThreatType.valueOf(request.threatType().toUpperCase());
            } catch (IllegalArgumentException e) {
                threatType = ThreatType.UNKNOWN;
            }
        }

        int confidence = request.confidence() != null ? request.confidence() : 50;

        Ioc ioc = new Ioc(type, request.value(), request.source(), confidence, threatType, request.description());
        ioc = iocRepository.save(ioc);
        return toResponse(ioc);
    }

    public Optional<IocResponse> findById(String id) {
        return iocRepository.findById(id).map(this::toResponse);
    }

    public List<IocResponse> findAll() {
        return iocRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<IocResponse> findByType(IocType type) {
        return iocRepository.findByType(type).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<IocResponse> findByThreatType(ThreatType threatType) {
        return iocRepository.findByThreatType(threatType).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<IocResponse> searchByValue(String value) {
        return iocRepository.findByValueContaining(value).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public IocResponse updateLastSeen(String id) {
        Ioc ioc = iocRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("IOC not found: " + id));
        ioc.setLastSeen(Instant.now());
        ioc = iocRepository.save(ioc);
        return toResponse(ioc);
    }

    @Transactional
    public IocResponse deactivate(String id) {
        Ioc ioc = iocRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("IOC not found: " + id));
        ioc.setActive(false);
        ioc = iocRepository.save(ioc);
        return toResponse(ioc);
    }

    public long countByType(IocType type) {
        return iocRepository.findByType(type).size();
    }

    private IocResponse toResponse(Ioc i) {
        return new IocResponse(
            i.getId(),
            i.getType().name(),
            i.getValue(),
            i.getSource(),
            i.getConfidence(),
            i.getThreatType().name(),
            i.getDescription(),
            i.getFirstSeen().toString(),
            i.getLastSeen() != null ? i.getLastSeen().toString() : null,
            i.isActive()
        );
    }
}
