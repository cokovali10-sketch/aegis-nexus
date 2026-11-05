package com.aegis.nexus.modules.intel.controller;

import com.aegis.nexus.modules.intel.dto.IocCreateRequest;
import com.aegis.nexus.modules.intel.dto.IocResponse;
import com.aegis.nexus.modules.intel.service.IocService;
import com.aegis.nexus.modules.intel.service.OsintService;
import com.aegis.nexus.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/intel")
@PreAuthorize("hasRole('ROLE_ANALYST') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
public class IntelController {

    private final IocService iocService;
    private final OsintService osintService;

    public IntelController(IocService iocService, OsintService osintService) {
        this.iocService = iocService;
        this.osintService = osintService;
    }

    @PostMapping("/iocs")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<IocResponse>> createIoc(@RequestBody IocCreateRequest request) {
        IocResponse response = iocService.createIoc(request);
        return ResponseEntity.ok(ApiResponse.ok(response, "IOC created successfully"));
    }

    @GetMapping("/iocs")
    public ResponseEntity<ApiResponse<List<IocResponse>>> listIocs() {
        List<IocResponse> iocs = iocService.findAll();
        return ResponseEntity.ok(ApiResponse.ok(iocs));
    }

    @GetMapping("/iocs/{id}")
    public ResponseEntity<ApiResponse<IocResponse>> getIoc(@PathVariable String id) {
        return iocService.findById(id)
            .map(i -> ResponseEntity.ok(ApiResponse.ok(i)))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/iocs/search")
    public ResponseEntity<ApiResponse<List<IocResponse>>> searchIocs(@RequestParam String value) {
        List<IocResponse> iocs = iocService.searchByValue(value);
        return ResponseEntity.ok(ApiResponse.ok(iocs));
    }

    @GetMapping("/iocs/type/{type}")
    public ResponseEntity<ApiResponse<List<IocResponse>>> listByType(@PathVariable String type) {
        List<IocResponse> iocs = iocService.findByType(
            com.aegis.nexus.modules.intel.model.IocType.valueOf(type.toUpperCase()));
        return ResponseEntity.ok(ApiResponse.ok(iocs));
    }

    @PostMapping("/iocs/{id}/seen")
    public ResponseEntity<ApiResponse<IocResponse>> updateLastSeen(@PathVariable String id) {
        IocResponse response = iocService.updateLastSeen(id);
        return ResponseEntity.ok(ApiResponse.ok(response, "Last seen updated"));
    }

    @PostMapping("/iocs/{id}/deactivate")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<IocResponse>> deactivate(@PathVariable String id) {
        IocResponse response = iocService.deactivate(id);
        return ResponseEntity.ok(ApiResponse.ok(response, "IOC deactivated"));
    }

    @GetMapping("/osint/ip/{ip}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> analyzeIp(@PathVariable String ip) {
        Map<String, Object> result = osintService.analyzeIp(ip);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/osint/domain/{domain}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> analyzeDomain(@PathVariable String domain) {
        Map<String, Object> result = osintService.analyzeDomain(domain);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/osint/hash/{hash}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> analyzeHash(@PathVariable String hash) {
        Map<String, Object> result = osintService.analyzeHash(hash);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }
}
