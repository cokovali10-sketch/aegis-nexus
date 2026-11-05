package com.aegis.nexus.modules.edr.controller;

import com.aegis.nexus.modules.edr.dto.FileIntegrityResponse;
import com.aegis.nexus.modules.edr.service.FileIntegrityService;
import com.aegis.nexus.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/edr/file-integrity")
@PreAuthorize("hasRole('ROLE_ANALYST') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
public class FileIntegrityController {

    private final FileIntegrityService fileIntegrityService;

    public FileIntegrityController(FileIntegrityService fileIntegrityService) {
        this.fileIntegrityService = fileIntegrityService;
    }

    @GetMapping("/endpoint/{endpointId}")
    public ResponseEntity<ApiResponse<List<FileIntegrityResponse>>> listByEndpoint(@PathVariable String endpointId) {
        List<FileIntegrityResponse> records = fileIntegrityService.findByEndpointId(endpointId);
        return ResponseEntity.ok(ApiResponse.ok(records));
    }

    @GetMapping("/unauthorized")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<List<FileIntegrityResponse>>> listUnauthorized() {
        List<FileIntegrityResponse> records = fileIntegrityService.findUnauthorized();
        return ResponseEntity.ok(ApiResponse.ok(records));
    }

    @PostMapping("/endpoint/{endpointId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<FileIntegrityResponse>> createRecord(@PathVariable String endpointId,
                                                                            @RequestParam String filePath,
                                                                            @RequestParam String sha256,
                                                                            @RequestParam long fileSize,
                                                                            @RequestParam String changeType) {
        FileIntegrityResponse response = fileIntegrityService.createRecord(endpointId, filePath, sha256, fileSize, changeType);
        return ResponseEntity.ok(ApiResponse.ok(response, "File integrity record created"));
    }

    @PostMapping("/{id}/authorize")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<FileIntegrityResponse>> markAuthorized(@PathVariable String id,
                                                                              @RequestParam boolean authorized) {
        FileIntegrityResponse response = fileIntegrityService.markAuthorized(id, authorized);
        return ResponseEntity.ok(ApiResponse.ok(response, "File integrity authorization updated"));
    }
}
