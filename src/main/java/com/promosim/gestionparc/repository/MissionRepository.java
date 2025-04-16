package com.promosim.gestionparc.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.promosim.gestionparc.model.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long>, JpaSpecificationExecutor<Mission> {
    boolean existsByVehicleIdAndDoneFalse(Long vehicleId);

    @Query("SELECT m.vehicle.id FROM Mission m WHERE m.vehicle IS NOT NULL AND (m.arrivalDate IS NULL OR m.arrivalDate > CURRENT_DATE)")
    List<Long> findBusyVehicleIds();

    @Query("SELECT m.driver.id FROM Mission m WHERE m.driver IS NOT NULL AND (m.arrivalDate IS NULL OR m.arrivalDate > CURRENT_DATE)")
    List<Long> findBusyDriverIds();



}
