package com.aegis.nexus.modules.assets.service;

import com.aegis.nexus.modules.assets.dto.AssetCreateRequest;
import com.aegis.nexus.modules.assets.dto.AssetResponse;
import com.aegis.nexus.modules.assets.model.Asset;
import com.aegis.nexus.modules.assets.repository.AssetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AssetService {

    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Transactional
    public AssetResponse createAsset(AssetCreateRequest request) {
        Asset asset = new Asset(
            request.name(),
            request.type(),
            request.ipAddress(),
            request.hostname(),
            request.os(),
            request.owner()
        );
        asset.setOsVersion(request.osVersion());
        asset.setDepartment(request.department());
        asset.setLocation(request.location());
        asset.setRiskScore(request.riskScore() != null ? request.riskScore() : 0);
        asset.setCritical(request.isCritical() != null ? request.isCritical() : false);
        asset.setTags(request.tags());
        asset = assetRepository.save(asset);
        return toResponse(asset);
    }

    public AssetResponse findById(String id) {
        return assetRepository.findById(id).map(this::toResponse).orElse(null);
    }

    public List<AssetResponse> findAll() {
        return assetRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<AssetResponse> findByType(String type) {
        return assetRepository.findByType(type).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<AssetResponse> findByOwner(String owner) {
        return assetRepository.findByOwner(owner).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<AssetResponse> searchByName(String name) {
        return assetRepository.findByNameContaining(name).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public AssetResponse updateRiskScore(String id, int riskScore) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Asset not found: " + id));
        asset.setRiskScore(riskScore);
        asset.setUpdatedAt(Instant.now());
        asset = assetRepository.save(asset);
        return toResponse(asset);
    }

    @Transactional
    public AssetResponse setCritical(String id, boolean critical) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Asset not found: " + id));
        asset.setCritical(critical);
        asset.setUpdatedAt(Instant.now());
        asset = assetRepository.save(asset);
        return toResponse(asset);
    }

    private AssetResponse toResponse(Asset a) {
        return new AssetResponse(
            a.getId(), a.getName(), a.getType(), a.getIpAddress(), a.getHostname(),
            a.getOs(), a.getOsVersion(), a.getOwner(), a.getDepartment(), a.getLocation(),
            a.getRiskScore(), a.isCritical(), a.getTags(),
            a.getCreatedAt().toString(),
            a.getUpdatedAt() != null ? a.getUpdatedAt().toString() : null
        );
    }
}


