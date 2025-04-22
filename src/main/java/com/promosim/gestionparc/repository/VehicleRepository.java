package com.promosim.gestionparc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.promosim.gestionparc.model.Vehicle;

import org.springframework.lang.NonNull;
import jakarta.transaction.Transactional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {

    


    void deleteById(@NonNull Long id);
    List<Vehicle> findByIdNotIn(List<Long> ids);


    @Transactional
    @Modifying
    @Query("DELETE FROM Vehicle v WHERE v.id = :id")
    void deleteByVin(@Param("id") @NonNull Long id);

    @Query("SELECT COUNT(m) > 0 FROM Mission m WHERE m.vehicle.id = :vehicleId AND m.done = false")
boolean isVehicleAssignedToOngoingMission(@Param("vehicleId") Long vehicleId);


}