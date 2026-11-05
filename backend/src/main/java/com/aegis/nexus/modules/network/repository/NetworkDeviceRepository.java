package com.aegis.nexus.modules.network.repository;

import com.aegis.nexus.modules.network.model.NetworkDevice;
import com.aegis.nexus.modules.network.model.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetworkDeviceRepository extends JpaRepository<NetworkDevice, String> {
    List<NetworkDevice> findByStatus(DeviceStatus status);
    List<NetworkDevice> findByIpAddress(String ipAddress);
    List<NetworkDevice> findByDeviceType(String deviceType);
    List<NetworkDevice> findByIsManaged(boolean managed);
    List<NetworkDevice> findByHostname(String hostname);
}
