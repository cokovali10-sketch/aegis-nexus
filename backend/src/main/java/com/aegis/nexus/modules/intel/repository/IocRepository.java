package com.aegis.nexus.modules.intel.repository;

import com.aegis.nexus.modules.intel.model.Ioc;
import com.aegis.nexus.modules.intel.model.IocType;
import com.aegis.nexus.modules.intel.model.ThreatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IocRepository extends JpaRepository<Ioc, String> {
    Optional<Ioc> findByTypeAndValue(IocType type, String value);
    List<Ioc> findByType(IocType type);
    List<Ioc> findByThreatType(ThreatType threatType);
    List<Ioc> findByIsActive(boolean active);
    List<Ioc> findByValueContaining(String value);
    boolean existsByTypeAndValue(IocType type, String value);
}
