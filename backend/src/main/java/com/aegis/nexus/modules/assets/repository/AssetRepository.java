package com.aegis.nexus.modules.assets.repository;

import com.aegis.nexus.modules.assets.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {
    List<Asset> findByType(String type);
    List<Asset> findByOwner(String owner);
    List<Asset> findByDepartment(String department);
    List<Asset> findByIsCritical(boolean isCritical);
    List<Asset> findByNameContaining(String name);
}
