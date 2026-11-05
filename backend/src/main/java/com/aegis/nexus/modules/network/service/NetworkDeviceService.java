package com.aegis.nexus.modules.network.service;

import com.aegis.nexus.modules.network.dto.NetworkDeviceCreateRequest;
import com.aegis.nexus.modules.network.dto.NetworkDeviceResponse;
import com.aegis.nexus.modules.network.model.DeviceStatus;
import com.aegis.nexus.modules.network.model.NetworkDevice;
import com.aegis.nexus.modules.network.repository.NetworkDeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class NetworkDeviceService {

    private final NetworkDeviceRepository networkDeviceRepository;

    public NetworkDeviceService(NetworkDeviceRepository networkDeviceRepository) {
        this.networkDeviceRepository = networkDeviceRepository;
    }

    @Transactional
    public NetworkDeviceResponse createDevice(NetworkDeviceCreateRequest request) {
        DeviceStatus status = request.isManaged() != null && request.isManaged() ? DeviceStatus.ONLINE : DeviceStatus.UNKNOWN;

        NetworkDevice device = new NetworkDevice(
            request.ipAddress(), request.macAddress(), request.hostname(),
            request.deviceType(), request.vendor()
        );
        device.setOs(request.os());
        device.setStatus(status);
        device.setManaged(request.isManaged() != null ? request.isManaged() : false);
        device.setRiskScore(request.riskScore() != null ? request.riskScore() : 0);
        device.setLastSeen(Instant.now());
        device = networkDeviceRepository.save(device);
        return toResponse(device);
    }

    public NetworkDeviceResponse findById(String id) {
        return networkDeviceRepository.findById(id).map(this::toResponse).orElse(null);
    }

    public List<NetworkDeviceResponse> findAll() {
        return networkDeviceRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<NetworkDeviceResponse> findByStatus(DeviceStatus status) {
        return networkDeviceRepository.findByStatus(status).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<NetworkDeviceResponse> findUnmanaged() {
        return networkDeviceRepository.findByIsManaged(false).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public NetworkDeviceResponse updateStatus(String id, DeviceStatus status) {
        NetworkDevice device = networkDeviceRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Device not found: " + id));
        device.setStatus(status);
        device.setLastSeen(Instant.now());
        device = networkDeviceRepository.save(device);
        return toResponse(device);
    }

    private NetworkDeviceResponse toResponse(NetworkDevice d) {
        return new NetworkDeviceResponse(
            d.getId(), d.getIpAddress(), d.getMacAddress(), d.getHostname(),
            d.getDeviceType(), d.getVendor(), d.getOs(), d.getStatus().name(),
            d.getLastSeen().toString(), d.isManaged(), d.getRiskScore()
        );
    }
}


