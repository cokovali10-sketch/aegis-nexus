package com.aegis.nexus.modules.edr;

import com.aegis.nexus.modules.edr.model.FileIntegrityRecord;
import com.aegis.nexus.modules.edr.repository.FileIntegrityRepository;
import com.aegis.nexus.modules.edr.service.FileIntegrityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileIntegrityServiceTest {

    @Mock
    private FileIntegrityRepository fileIntegrityRepository;

    @InjectMocks
    private FileIntegrityService fileIntegrityService;

    @Test
    void createRecordSucceeds() {
        when(fileIntegrityRepository.save(any(FileIntegrityRecord.class))).thenAnswer(invocation -> {
            FileIntegrityRecord r = invocation.getArgument(0);
            r.setId("fim-1");
            return r;
        });

        var response = fileIntegrityService.createRecord("ep-1", "C:\\Windows\\System32\\kernel32.dll",
            "abc123def456", 1024000, "MODIFIED");

        assertThat(response.filePath()).isEqualTo("C:\\Windows\\System32\\kernel32.dll");
        assertThat(response.changeType()).isEqualTo("MODIFIED");
        assertThat(response.isAuthorized()).isFalse();
    }

    @Test
    void markAuthorizedWorks() {
        FileIntegrityRecord record = new FileIntegrityRecord("ep-1", "C:\\test.dll", "sha256", 100, "CREATED");
        record.setId("fim-1");

        when(fileIntegrityRepository.findById("fim-1")).thenReturn(Optional.of(record));
        when(fileIntegrityRepository.save(any(FileIntegrityRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = fileIntegrityService.markAuthorized("fim-1", true);

        assertThat(response.isAuthorized()).isTrue();
    }

    @Test
    void markAuthorizedNotFoundThrows() {
        when(fileIntegrityRepository.findById("fim-999")).thenReturn(Optional.empty());
        assertThat(org.assertj.core.api.Assertions.catchThrowable(() ->
            fileIntegrityService.markAuthorized("fim-999", true)))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
