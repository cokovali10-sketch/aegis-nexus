package com.aegis.nexus.modules.intel;

import com.aegis.nexus.modules.intel.dto.IocCreateRequest;
import com.aegis.nexus.modules.intel.model.Ioc;
import com.aegis.nexus.modules.intel.model.IocType;
import com.aegis.nexus.modules.intel.model.ThreatType;
import com.aegis.nexus.modules.intel.repository.IocRepository;
import com.aegis.nexus.modules.intel.service.IocService;
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
class IocServiceTest {

    @Mock
    private IocRepository iocRepository;

    @InjectMocks
    private IocService iocService;

    @Test
    void createIocSucceeds() {
        when(iocRepository.existsByTypeAndValue(IocType.IP, "192.168.1.100")).thenReturn(false);
        when(iocRepository.save(any(Ioc.class))).thenAnswer(invocation -> {
            Ioc i = invocation.getArgument(0);
            i.setId("ioc-1");
            return i;
        });

        var request = new IocCreateRequest("IP", "192.168.1.100", "threat-feed", 85, "MALWARE", "Test IOC");
        var response = iocService.createIoc(request);

        assertThat(response.type()).isEqualTo("IP");
        assertThat(response.value()).isEqualTo("192.168.1.100");
        assertThat(response.confidence()).isEqualTo(85);
        assertThat(response.threatType()).isEqualTo("MALWARE");
    }

    @Test
    void createDuplicateIocThrows() {
        when(iocRepository.existsByTypeAndValue(IocType.IP, "192.168.1.100")).thenReturn(true);

        var request = new IocCreateRequest("IP", "192.168.1.100", "threat-feed", 85, "MALWARE", "Test IOC");
        assertThat(org.assertj.core.api.Assertions.catchThrowable(() -> iocService.createIoc(request)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createInvalidTypeThrows() {
        var request = new IocCreateRequest("INVALID_TYPE", "192.168.1.100", "threat-feed", 85, "MALWARE", "Test IOC");
        assertThat(org.assertj.core.api.Assertions.catchThrowable(() -> iocService.createIoc(request)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deactivateIocWorks() {
        Ioc ioc = new Ioc(IocType.IP, "192.168.1.100", "feed", 80, ThreatType.MALWARE, "test");
        ioc.setId("ioc-1");

        when(iocRepository.findById("ioc-1")).thenReturn(Optional.of(ioc));
        when(iocRepository.save(any(Ioc.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = iocService.deactivate("ioc-1");
        assertThat(response.isActive()).isFalse();
    }

    @Test
    void searchByValueReturnsResults() {
        when(iocRepository.findByValueContaining("192.168")).thenReturn(List.of());
        var results = iocService.searchByValue("192.168");
        assertThat(results).isEmpty();
    }

    @Test
    void countByTypeWorks() {
        when(iocRepository.findByType(IocType.IP)).thenReturn(List.of());
        long count = iocService.countByType(IocType.IP);
        assertThat(count).isEqualTo(0);
    }
}
