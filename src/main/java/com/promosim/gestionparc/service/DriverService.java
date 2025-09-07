package com.promosim.gestionparc.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.promosim.gestionparc.enums.MissionStatus;
import com.promosim.gestionparc.model.Driver;
import com.promosim.gestionparc.model.Mission;
import com.promosim.gestionparc.repository.DriverRepository;
import com.promosim.gestionparc.specification.DriverSpecifications;
import com.promosim.gestionparc.repository.MissionRepository;

import jakarta.transaction.Transactional;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private MissionRepository missionRepository;

    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }
    public Driver getDriverById(Long id) {
        return driverRepository.findById(id).orElse(null); // Return null if not found)
    }


    public List<Driver> searchDrivers(Long id, String firstName, String lastName, String phoneNumber, String licenseNumber, String homeAddress, LocalDate dateOfBirth) {
        Specification<Driver> spec = Specification.where(null);

        if(id != null){
            spec = spec.and(DriverSpecifications.hasId(id));
        }
        if(firstName != null && !firstName.isEmpty()){
            spec = spec.and(DriverSpecifications.hasFirstName(firstName.trim()));
        }
        if(lastName != null && !lastName.isEmpty()){
            spec = spec.and(DriverSpecifications.hasLastName(lastName.trim()));
        }
        if(phoneNumber != null && !phoneNumber.isEmpty()){
            spec = spec.and(DriverSpecifications.hasPhoneNumber(phoneNumber.trim()));
        }
        if(licenseNumber != null && !licenseNumber.isEmpty()){
            spec = spec.and(DriverSpecifications.hasLicenseNumber(licenseNumber.trim()));
        }
        if(homeAddress != null && !homeAddress.isEmpty()){
            spec = spec.and(DriverSpecifications.hasHomeAddress(homeAddress.trim()));
        }
        if(dateOfBirth != null){
            spec = spec.and(DriverSpecifications.hasDateOfBirth(dateOfBirth));
        }
        return driverRepository.findAll(spec);
    }

    @Transactional
    public Driver updateDriver(Driver updatedDriver){
        return driverRepository.findById(updatedDriver.getId()).map(existingDriver -> {
            if(updatedDriver.getFirstName() != null) {
                existingDriver.setFirstName(updatedDriver.getFirstName());
            }
            if(updatedDriver.getLastName() != null) {
                existingDriver.setLastName(updatedDriver.getLastName());
            }
            if(updatedDriver.getLicenseNumber() != null) {
                existingDriver.setLicenseNumber(updatedDriver.getLicenseNumber());
            }
            
            if(updatedDriver.getPhoneNumber() != null) {
                existingDriver.setPhoneNumber(updatedDriver.getPhoneNumber());
            }
            if(updatedDriver.getHomeAddress() != null) {
                existingDriver.setHomeAddress(updatedDriver.getHomeAddress());
            }
            if(updatedDriver.getDateOfBirth() != null) {
                existingDriver.setDateOfBirth(updatedDriver.getDateOfBirth());
            }
            return driverRepository.save(existingDriver);
        })
        .orElseThrow(() -> new RuntimeException("Driver not found with id: " + updatedDriver.getId()));
    }
    @Transactional
    public void deleteDriverById(Long id) {
        try {
            Optional<Driver> optionalDriver = driverRepository.findById(id);
            if (optionalDriver.isPresent()) {
                Driver driver = optionalDriver.get();

                boolean isDriverAssignedToAnyMission = missionRepository.existsByDriverIdAndStatus(driver.getId(), MissionStatus.ONGOING);
                if (isDriverAssignedToAnyMission) {
                    System.out.println("Driver with ID " + id + " is assigned to an ongoing mission and cannot be deleted."); // Add logging
                    throw new IllegalStateException("Ce conducteur est assigné à une mission en cours et ne peut pas être supprimé.");
                }
                driverRepository.delete(driver);
                System.out.println("Driver with ID " + id + " has been deleted."); // Add logging
            } else {
                System.out.println("Driver with ID " + id + " not found."); // Add logging
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log any errors
            throw new RuntimeException("Error deleting Driver with VIN: " + id, e);
        }
        }

    public List<Driver> getAvailableDrivers() {
        List<Long> busyIds = missionRepository.findBusyDriverIds();
        if (busyIds.isEmpty()) {
            return driverRepository.findAll();
        }
        return driverRepository.findByIdNotIn(busyIds);
    }

    public boolean isDriverAssignedToAnyMission(Long id) {
        return missionRepository.existsByDriverIdAndStatus(id, MissionStatus.ONGOING);
    }

    public void unlinkDriverFromMissions(Long driverId) {
        List<Mission> missions = missionRepository.findByDriverId(driverId);
        for (Mission mission : missions) {
            mission.setDriver(null);
            missionRepository.save(mission);
        }
        
       
    }



    


}
