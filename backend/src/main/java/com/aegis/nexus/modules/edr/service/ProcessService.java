package com.aegis.nexus.modules.edr.service;

import com.aegis.nexus.modules.edr.dto.ProcessResponse;
import com.aegis.nexus.modules.edr.model.Process;
import com.aegis.nexus.modules.edr.repository.ProcessRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProcessService {

    private final ProcessRepository processRepository;

    public ProcessService(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    public List<ProcessResponse> findByEndpointId(String endpointId) {
        return processRepository.findByEndpointId(endpointId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<ProcessResponse> findSuspiciousByEndpointId(String endpointId) {
        return processRepository.findByEndpointIdAndIsSuspicious(endpointId, true).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public ProcessResponse createProcess(String endpointId, int pid, String name, String executablePath,
                                         String commandLine, Integer parentPid, String user) {
        Process process = new Process(endpointId, pid, name, executablePath, commandLine, parentPid, user);
        process = processRepository.save(process);
        return toResponse(process);
    }

    @Transactional
    public ProcessResponse markSuspicious(String id, boolean suspicious) {
        Process process = processRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Process not found: " + id));
        process.setSuspicious(suspicious);
        process = processRepository.save(process);
        return toResponse(process);
    }

    private ProcessResponse toResponse(Process p) {
        return new ProcessResponse(
            p.getId(),
            p.getEndpointId(),
            p.getPid(),
            p.getName(),
            p.getExecutablePath(),
            p.getCommandLine(),
            p.getParentPid(),
            p.getUser(),
            p.getCpuUsage(),
            p.getMemoryUsageMB(),
            p.getStartedAt().toString(),
            p.isSuspicious()
        );
    }
}


