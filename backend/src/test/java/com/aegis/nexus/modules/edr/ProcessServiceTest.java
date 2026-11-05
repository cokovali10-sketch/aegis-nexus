package com.aegis.nexus.modules.edr;

import com.aegis.nexus.modules.edr.model.Process;
import com.aegis.nexus.modules.edr.repository.ProcessRepository;
import com.aegis.nexus.modules.edr.service.ProcessService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessServiceTest {

    @Mock
    private ProcessRepository processRepository;

    @InjectMocks
    private ProcessService processService;

    @Test
    void createProcessSucceeds() {
        when(processRepository.save(any(Process.class))).thenAnswer(invocation -> {
            Process p = invocation.getArgument(0);
            p.setId("proc-1");
            p.setStartedAt(Instant.now());
            return p;
        });

        var response = processService.createProcess("ep-1", 1234, "powershell.exe",
            "C:\\Windows\\System32\\powershell.exe", "-Command Get-Process", 567, "admin");

        assertThat(response.name()).isEqualTo("powershell.exe");
        assertThat(response.pid()).isEqualTo(1234);
        assertThat(response.isSuspicious()).isFalse();
    }

    @Test
    void markSuspiciousWorks() {
        Process process = new Process("ep-1", 1234, "test.exe", "C:\\test.exe", "", null, "user");
        process.setId("proc-1");

        when(processRepository.findById("proc-1")).thenReturn(Optional.of(process));
        when(processRepository.save(any(Process.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = processService.markSuspicious("proc-1", true);

        assertThat(response.isSuspicious()).isTrue();
    }

    @Test
    void markSuspiciousNotFoundThrows() {
        when(processRepository.findById("proc-999")).thenReturn(Optional.empty());
        assertThat(org.assertj.core.api.Assertions.catchThrowable(() ->
            processService.markSuspicious("proc-999", true)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findByEndpointIdReturnsList() {
        when(processRepository.findByEndpointId("ep-1")).thenReturn(List.of());
        var result = processService.findByEndpointId("ep-1");
        assertThat(result).isEmpty();
    }
}
