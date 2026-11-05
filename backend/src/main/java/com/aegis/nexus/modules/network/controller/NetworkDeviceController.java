package com.aegis.nexus.modules.network.controller;

import com.aegis.nexus.modules.network.dto.NetworkDeviceCreateRequest;
import com.aegis.nexus.modules.network.dto.NetworkDeviceResponse;
import com.aegis.nexus.modules.network.service.NetworkDeviceService;
import com.aegis.nexus.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/network")
@PreAuthorize("hasRole('ROLE_ANALYST') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
public class NetworkDeviceController {

    private final NetworkDeviceService networkDeviceService;

    public NetworkDeviceController(NetworkDeviceService networkDeviceService) {
        this.networkDeviceService = networkDeviceService;
    }

    @PostMapping("/devices")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<NetworkDeviceResponse>> createDevice(@RequestBody NetworkDeviceCreateRequest request) {
        NetworkDeviceResponse response = networkDeviceService.createDevice(request);
        return ResponseEntity.ok(ApiResponse.ok(response, "Device discovered"));
    }

    @GetMapping("/devices")
    public ResponseEntity<ApiResponse<List<NetworkDeviceResponse>>> listDevices() {
        List<NetworkDeviceResponse> devices = networkDeviceService.findAll();
        return ResponseEntity.ok(ApiResponse.ok(devices));
    }

    @GetMapping("/devices/{id}")
    public ResponseEntity<ApiResponse<NetworkDeviceResponse>> getDevice(@PathVariable String id) {
        NetworkDeviceResponse response = networkDeviceService.findById(id);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/devices/status/{status}")
    public ResponseEntity<ApiResponse<List<NetworkDeviceResponse>>> listByStatus(@PathVariable String status) {
        List<NetworkDeviceResponse> devices = networkDeviceService.findByStatus(
            com.aegis.nexus.modules.network.model.DeviceStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(ApiResponse.ok(devices));
    }

    @GetMapping("/devices/unmanaged")
    public ResponseEntity<ApiResponse<List<NetworkDeviceResponse>>> listUnmanaged() {
        List<NetworkDeviceResponse> devices = networkDeviceService.findUnmanaged();
        return ResponseEntity.ok(ApiResponse.ok(devices));
    }

    @PostMapping("/devices/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<NetworkDeviceResponse>> updateStatus(@PathVariable String id, @RequestParam String status) {
        NetworkDeviceResponse response = networkDeviceService.updateStatus(id,
            com.aegis.nexus.modules.network.model.DeviceStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(ApiResponse.ok(response, "Device status updated"));
    }
}


