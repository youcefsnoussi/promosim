package com.promosim.gestionparc.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.promosim.gestionparc.model.Driver;
import com.promosim.gestionparc.repository.DriverRepository;
import com.promosim.gestionparc.specification.DriverSpecifications;

import jakarta.transaction.Transactional;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
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
    public void deleteDriverById(Long id) {
        driverRepository.deleteById(id);
    }



    


}
