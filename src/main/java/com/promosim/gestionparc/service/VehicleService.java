package com.promosim.gestionparc.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.promosim.gestionparc.model.Vehicle;
import com.promosim.gestionparc.repository.MissionRepository;
import com.promosim.gestionparc.repository.VehicleRepository;
import com.promosim.gestionparc.specification.VehicleSpecifications;

import jakarta.transaction.Transactional;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private MissionRepository missionRepository;

    public List<Vehicle> getAvailableVehicles() {
        List<Long> busyIds = missionRepository.findBusyVehicleIds();
        if (busyIds.isEmpty()) {
            return vehicleRepository.findAll();
        }
        return vehicleRepository.findByIdNotIn(busyIds);
    }
    



    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
    public void deleteVehicleByVin(String vin) {
        vehicleRepository.deleteByVin(vin);
    }
    public Vehicle getVehicleByVin(String vin) {
        return vehicleRepository.findByVin(vin).orElse(null);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> searchVehicles(Long id, String brand, String model, String plateNumber, String type, String fuelType, String vin, LocalDate lastServiceDate, LocalDate nextServiceDate,
     LocalDate insuranceExpiryDate, String address, LocalDate nextMaintenanceDate, String maintenanceType) {
        Specification<Vehicle> spec = Specification.where(null);
        if(id != null){
            spec = spec.and(VehicleSpecifications.hasId(id));
        }

        if (brand != null && !brand.isEmpty()) {
            spec = spec.and(VehicleSpecifications.hasBrand(brand.trim()));
        }
        if (model != null && !model.isEmpty()) {
            spec = spec.and(VehicleSpecifications.hasModel(model.trim()));
        }
        if (plateNumber != null && !plateNumber.isEmpty()) {
            spec = spec.and(VehicleSpecifications.hasPlateNumber(plateNumber.trim()));
        }
        if (type != null) {
            spec = spec.and(VehicleSpecifications.hasType(type.trim()));
        }
        if (fuelType != null) {
            spec = spec.and(VehicleSpecifications.hasFuelType(fuelType.trim()));
        }
        if (vin != null && !vin.isEmpty()) {
            spec = spec.and(VehicleSpecifications.hasVin(vin.trim()));
        }
        if (lastServiceDate != null) {
            spec = spec.and(VehicleSpecifications.hasLastServiceDate(lastServiceDate));
        }
        if (nextServiceDate != null) {
            spec = spec.and(VehicleSpecifications.hasNextServiceDate(nextServiceDate));
        }
        if (insuranceExpiryDate != null) {
            spec = spec.and(VehicleSpecifications.hasInsuranceExpiryDate(insuranceExpiryDate));
        }
        if (address != null && !address.isEmpty()) {
            spec = spec.and(VehicleSpecifications.hasAddress(address.trim()));
        }
        if (nextMaintenanceDate != null) {
            spec = spec.and(VehicleSpecifications.hasNextMaintenanceDate(nextMaintenanceDate));
        }
        if (maintenanceType != null && !maintenanceType.isEmpty()) {
            spec = spec.and(VehicleSpecifications.hasMaintenanceType(maintenanceType.trim()));
        }
        return vehicleRepository.findAll(spec);
    }

    @Transactional
    public Vehicle updateVehicle(Vehicle updatedVehicle) {
        return vehicleRepository.findByVin(updatedVehicle.getVin())
            .map(existingVehicle -> {
                if (updatedVehicle.getMileage() != null) {
                    existingVehicle.setMileage(updatedVehicle.getMileage());
                }
                if (updatedVehicle.getLastServiceDate() != null) {
                    existingVehicle.setLastServiceDate(updatedVehicle.getLastServiceDate());
                }
                if (updatedVehicle.getNextServiceDate() != null) {
                    existingVehicle.setNextServiceDate(updatedVehicle.getNextServiceDate());
                }
                if (updatedVehicle.getInsuranceExpiryDate() != null) {
                    existingVehicle.setInsuranceExpiryDate(updatedVehicle.getInsuranceExpiryDate());
                }
                if (updatedVehicle.getNextMaintenanceDate() != null) {
                    existingVehicle.setNextMaintenanceDate(updatedVehicle.getNextMaintenanceDate());
                }
                if (updatedVehicle.getMaintenanceType() != null && !updatedVehicle.getMaintenanceType().isEmpty()) {
                    existingVehicle.setMaintenanceType(updatedVehicle.getMaintenanceType());
                }
                if (updatedVehicle.getAddress() != null && !updatedVehicle.getAddress().isEmpty()) {
                    existingVehicle.setAddress(updatedVehicle.getAddress());
                }
                // Save and return the updated vehicle
                return vehicleRepository.save(existingVehicle);
            })
            .orElseThrow(() -> new RuntimeException("Vehicle not found with VIN: " + updatedVehicle.getVin()));
    }

    
}







