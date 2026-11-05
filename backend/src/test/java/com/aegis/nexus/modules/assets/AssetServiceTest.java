package com.aegis.nexus.modules.assets;

import com.aegis.nexus.modules.assets.dto.AssetCreateRequest;
import com.aegis.nexus.modules.assets.model.Asset;
import com.aegis.nexus.modules.assets.repository.AssetRepository;
import com.aegis.nexus.modules.assets.service.AssetService;
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
class AssetServiceTest {
    @Mock private AssetRepository repo;
    @InjectMocks private AssetService service;

    @Test
    void createAssetSucceeds() {
        when(repo.save(any(Asset.class))).thenAnswer(i -> { Asset a = i.getArgument(0); a.setId("a1"); return a; });
        var r = service.createAsset(new AssetCreateRequest("Server1", "SERVER", "10.0.1.1", "srv1", "Linux", "22.04", "admin", "IT", "DC", 50, true, "prod"));
        assertThat(r.name()).isEqualTo("Server1");
        assertThat(r.isCritical()).isTrue();
    }

    @Test
    void updateRiskScoreWorks() {
        Asset a = new Asset(); a.setId("a1");
        when(repo.findById("a1")).thenReturn(Optional.of(a));
        when(repo.save(any(Asset.class))).thenAnswer(i -> i.getArgument(0));
        var r = service.updateRiskScore("a1", 75);
        assertThat(r.riskScore()).isEqualTo(75);
    }

    @Test
    void searchByNameReturnsEmpty() {
        when(repo.findByNameContaining("test")).thenReturn(List.of());
        assertThat(service.searchByName("test")).isEmpty();
    }
}
