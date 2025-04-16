package com.promosim.gestionparc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.promosim.gestionparc.model.Vehicle;

import org.springframework.lang.NonNull;
import jakarta.transaction.Transactional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {

    Optional<Vehicle> findByVin(String vin);
    List<Vehicle> findByIdNotIn(List<Long> ids);



    @Transactional
    @Modifying
    @Query("DELETE FROM Vehicle v WHERE v.vin = :vin")
    void deleteByVin(@Param("vin") @NonNull String vin);

    @Query("SELECT COUNT(m) > 0 FROM Mission m WHERE m.vehicle.id = :vehicleId AND m.done = false")
boolean isVehicleAssignedToOngoingMission(@Param("vehicleId") Long vehicleId);


}