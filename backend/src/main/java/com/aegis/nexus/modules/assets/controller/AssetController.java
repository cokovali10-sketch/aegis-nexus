package com.aegis.nexus.modules.assets.controller;

import com.aegis.nexus.modules.assets.dto.AssetCreateRequest;
import com.aegis.nexus.modules.assets.dto.AssetResponse;
import com.aegis.nexus.modules.assets.service.AssetService;
import com.aegis.nexus.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
@PreAuthorize("hasRole('ROLE_ANALYST') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<AssetResponse>> createAsset(@RequestBody AssetCreateRequest request) {
        AssetResponse response = assetService.createAsset(request);
        return ResponseEntity.ok(ApiResponse.ok(response, "Asset created"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AssetResponse>>> listAssets() {
        List<AssetResponse> assets = assetService.findAll();
        return ResponseEntity.ok(ApiResponse.ok(assets));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssetResponse>> getAsset(@PathVariable String id) {
        AssetResponse response = assetService.findById(id);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<AssetResponse>>> listByType(@PathVariable String type) {
        List<AssetResponse> assets = assetService.findByType(type);
        return ResponseEntity.ok(ApiResponse.ok(assets));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<AssetResponse>>> search(@RequestParam String name) {
        List<AssetResponse> assets = assetService.searchByName(name);
        return ResponseEntity.ok(ApiResponse.ok(assets));
    }

    @PostMapping("/{id}/risk")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<AssetResponse>> updateRisk(@PathVariable String id, @RequestParam int riskScore) {
        AssetResponse response = assetService.updateRiskScore(id, riskScore);
        return ResponseEntity.ok(ApiResponse.ok(response, "Risk score updated"));
    }

    @PostMapping("/{id}/critical")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<AssetResponse>> setCritical(@PathVariable String id, @RequestParam boolean critical) {
        AssetResponse response = assetService.setCritical(id, critical);
        return ResponseEntity.ok(ApiResponse.ok(response, "Critical status updated"));
    }
}
