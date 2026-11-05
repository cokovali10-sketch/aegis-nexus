package com.aegis.nexus.modules.network;

import com.aegis.nexus.modules.network.dto.NetworkDeviceCreateRequest;
import com.aegis.nexus.modules.network.model.NetworkDevice;
import com.aegis.nexus.modules.network.repository.NetworkDeviceRepository;
import com.aegis.nexus.modules.network.service.NetworkDeviceService;
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
class NetworkDeviceServiceTest {
    @Mock private NetworkDeviceRepository repo;
    @InjectMocks private NetworkDeviceService service;

    @Test
    void createDeviceSucceeds() {
        when(repo.save(any(NetworkDevice.class))).thenAnswer(i -> { NetworkDevice d = i.getArgument(0); d.setId("d1"); return d; });
        var r = service.createDevice(new NetworkDeviceCreateRequest("192.168.1.1", "00:11:22:33:44:55", "router1", "ROUTER", "Cisco", "IOS", true, 10));
        assertThat(r.ipAddress()).isEqualTo("192.168.1.1");
        assertThat(r.isManaged()).isTrue();
    }

    @Test
    void findUnmanagedReturnsEmpty() {
        when(repo.findByIsManaged(false)).thenReturn(List.of());
        assertThat(service.findUnmanaged()).isEmpty();
    }

    @Test
    void updateStatusWorks() {
        NetworkDevice d = new NetworkDevice(); d.setId("d1");
        when(repo.findById("d1")).thenReturn(Optional.of(d));
        when(repo.save(any(NetworkDevice.class))).thenAnswer(i -> i.getArgument(0));
        var r = service.updateStatus("d1", com.aegis.nexus.modules.network.model.DeviceStatus.ONLINE);
        assertThat(r.status()).isEqualTo("ONLINE");
    }
}
