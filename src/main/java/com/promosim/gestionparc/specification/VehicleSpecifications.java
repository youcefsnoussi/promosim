package com.promosim.gestionparc.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;
import com.promosim.gestionparc.model.Vehicle;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class VehicleSpecifications {
    public static Specification<Vehicle> hasBrand(String brand){
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (brand == null || brand.isEmpty()) {
                return null;
            }
            return builder.like(builder.lower(root.get("brand")), "%" + brand.toLowerCase() + "%");
        };
    }

    public static Specification<Vehicle> hasModel(String model){
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (model == null || model.isEmpty()) {
                return null;
            }
            return builder.like(builder.lower(root.get("model")), "%" + model.toLowerCase() + "%");
        };
    }

    public static Specification<Vehicle> hasPlateNumber(String plateNumber){
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (plateNumber == null || plateNumber.isEmpty()) {
                return null;
            }
            return builder.like(builder.lower(root.get("plateNumber")), "%" + plateNumber.toLowerCase() + "%");
        };
    }
    public static Specification<Vehicle> hasType(String type){
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (type == null || type.isEmpty()) {
                return null;
            }
            return builder.equal(root.get("type"), type);
        };
    }
    public static Specification<Vehicle> hasFuelType(String fuelType){
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (fuelType == null || fuelType.isEmpty()) {
                return null;
            }
            return builder.equal(root.get("fuelType"), fuelType);
        };
    }

    public static Specification<Vehicle> hasVin(String vin){
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (vin == null || vin.isEmpty()) {
                return null;
            }
            return builder.like(builder.lower(root.get("vin")), "%" + vin.toLowerCase() + "%");
        };
    }

    public static Specification<Vehicle> hasLastServiceDate(LocalDate lastServiceDate){
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (lastServiceDate == null) {
                return null;
            }
            return builder.equal(root.get("lastServiceDate"), lastServiceDate);
        };
    }

    public static Specification<Vehicle> hasNextServiceDate(LocalDate nextServiceDate){
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (nextServiceDate == null) {
                return null;
            }
            return builder.equal(root.get("nextServiceDate"), nextServiceDate);
        };
    }

    public static Specification<Vehicle> hasInsuranceExpiryDate(LocalDate insuranceExpiryDate){
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (insuranceExpiryDate == null) {
                return null;
            }
            return builder.equal(root.get("insuranceExpiryDate"), insuranceExpiryDate);
        };
    }

    public static Specification<Vehicle> hasAddress(String address){
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (address == null || address.isEmpty()) {
                return null;
            }
            return builder.like(builder.lower(root.get("address")), "%" + address.toLowerCase() + "%");
        };
    }

    public static Specification<Vehicle> hasNextMaintenanceDate(LocalDate nextMaintenanceDate){
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (nextMaintenanceDate == null) {
                return null;
            }
            return builder.equal(root.get("nextMaintenanceDate"), nextMaintenanceDate);
        };
    }

    public static Specification<Vehicle> hasMaintenanceType(String maintenanceType){
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (maintenanceType == null || maintenanceType.isEmpty()) {
                return null;
            }
            return builder.like(builder.lower(root.get("maintenanceType")), "%" + maintenanceType.toLowerCase() + "%");
        };
    }

    public static Specification<Vehicle> hasMileage(Long mileage){
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (mileage == null) {
                return null;
            }
            return builder.equal(root.get("mileage"), mileage);
        };
    }

    public static Specification<Vehicle> hasId(Long id){
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (id == null) {
                return null;
            }
            return builder.equal(root.get("id"), id);
        };
    }

    public static Specification<Vehicle> hasYear(int year){
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (year == 0) {
                return null;
            }
            return builder.equal(root.get("year"), year);
        };
    }
}
    





