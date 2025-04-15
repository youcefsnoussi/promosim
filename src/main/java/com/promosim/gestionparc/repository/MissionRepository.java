package com.promosim.gestionparc.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

import com.promosim.gestionparc.model.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long>, JpaSpecificationExecutor<Mission> {
    boolean existsByVehicleIdAndDoneFalse(Long vehicleId);


}
