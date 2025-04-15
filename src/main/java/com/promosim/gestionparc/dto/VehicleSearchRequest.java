package com.promosim.gestionparc.dto;

public class VehicleSearchRequest {
    private String plateNumber;
    private String brand;
    private String model;
    private int year;
    private Long mileage;
    private String type;
    private String fuelType;
    private String vin;
    private String lastServiceDate;
    private String nextServiceDate;
    private String insuranceExpiryDate;
    private String address;
    private String nextMaintenanceDate;
    private String maintenanceType;

    // Getters and Setters
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
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getFuelType() {
        return fuelType;
    }
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
    public String getVin() {
        return vin;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }
    public String getLastServiceDate() {
        return lastServiceDate;
    }
    public void setLastServiceDate(String lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }
    public String getNextServiceDate() {
        return nextServiceDate;
    }
    public void setNextServiceDate(String nextServiceDate) {
        this.nextServiceDate = nextServiceDate;
    }
    public String getInsuranceExpiryDate() {
        return insuranceExpiryDate;
    }
    public void setInsuranceExpiryDate(String insuranceExpiryDate) {
        this.insuranceExpiryDate = insuranceExpiryDate;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getNextMaintenanceDate() {
        return nextMaintenanceDate;
    }
    public void setNextMaintenanceDate(String nextMaintenanceDate) {
        this.nextMaintenanceDate = nextMaintenanceDate;
    }
    public String getMaintenanceType() {
        return maintenanceType;
    }
    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }


}
