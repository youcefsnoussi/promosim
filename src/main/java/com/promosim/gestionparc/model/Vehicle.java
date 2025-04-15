package com.promosim.gestionparc.model;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;

import com.promosim.gestionparc.enums.FuelType;
import com.promosim.gestionparc.enums.VehicleType;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String plateNumber;

    
    private String brand;
    private String model;
    private int year;
    private Long mileage;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    @Column(unique = true, nullable = false)
    private String vin;

    private LocalDate lastServiceDate;
    private LocalDate nextServiceDate;
    private LocalDate insuranceExpiryDate;

    private String address;

    private LocalDate nextMaintenanceDate;
    private String maintenanceType;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPlateNumber() {
        return plateNumber;
    }
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public Long getMileage() {
        return mileage;
    }
    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }
    public VehicleType getType() {
        return type;
    }
    public void setType(VehicleType type) {
        this.type = type;
    }
    public FuelType getFuelType() {
        return fuelType;
    }
    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }
    public String getVin() {
        return vin;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }
    public LocalDate getLastServiceDate() {
        return lastServiceDate;
    }
    public void setLastServiceDate(LocalDate lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }
    public LocalDate getNextServiceDate() {
        return nextServiceDate;
    }
    public void setNextServiceDate(LocalDate nextServiceDate) {
        this.nextServiceDate = nextServiceDate;
    }
    public LocalDate getInsuranceExpiryDate() {
        return insuranceExpiryDate;
    }
    public void setInsuranceExpiryDate(LocalDate insuranceExpiryDate) {
        this.insuranceExpiryDate = insuranceExpiryDate;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public LocalDate getNextMaintenanceDate() {
        return nextMaintenanceDate;
    }
    public void setNextMaintenanceDate(LocalDate nextMaintenanceDate) {
        this.nextMaintenanceDate = nextMaintenanceDate;
    }
    public String getMaintenanceType() {
        return maintenanceType;
    }
    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }


    

}
