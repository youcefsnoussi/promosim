package com.promosim.gestionparc.specification;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;
import com.promosim.gestionparc.model.Driver;
import com.promosim.gestionparc.model.Mission;
import com.promosim.gestionparc.model.Vehicle;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public class MissionSpecifications {
    public static Specification<Mission> hasId(Long id){
        return (Root<Mission> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (id == null) {
                return null;
            }
            return builder.equal(root.get("id"), id);
        };
    }
    public static Specification<Mission> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Mission> hasVehicle(String vehicle) {
        return (root, query, criteriaBuilder) -> {
            if (vehicle == null || vehicle.isEmpty()) {
                return null;
            }
            Join<Mission, Vehicle> vehicleJoin = root.join("vehicle", JoinType.INNER);
            Integer year = null;
            try {
                year = Integer.parseInt(vehicle);
            } catch (NumberFormatException e) {
                // Not a number, continue with the string search
            }
            if (year != null) {
                return criteriaBuilder.equal(vehicleJoin.get("year"), year);
            } else {
            return criteriaBuilder.or(
            criteriaBuilder.like(criteriaBuilder.lower(vehicleJoin.get("brand")), "%" + vehicle.toLowerCase() + "%"),
            criteriaBuilder.like(criteriaBuilder.lower(vehicleJoin.get("model")), "%" + vehicle.toLowerCase() + "%"),
            criteriaBuilder.like(criteriaBuilder.lower(vehicleJoin.get("plateNumber")), "%" + vehicle.toLowerCase() + "%"),
            criteriaBuilder.like(criteriaBuilder.lower(vehicleJoin.get("vin")), "%" + vehicle.toLowerCase() + "%")
            );
            }
        };
    }
    public static Specification<Mission> hasDriver(String driver) {
        return (root, query, criteriaBuilder) -> {
            if (driver == null || driver.isEmpty()) {
                return null;
            }
            Join<Mission, Driver> driverJoin = root.join("driver", JoinType.INNER);
            return criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(driverJoin.get("firstName")), "%" + driver.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(driverJoin.get("lastName")), "%" + driver.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(driverJoin.get("licenseNumber")), "%" + driver.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(driverJoin.get("phoneNumber")), "%" + driver.toLowerCase() + "%")
            );
        };
    }
    public static Specification<Mission> hasDestination(String destination) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("destination")), "%" + destination.toLowerCase() + "%");
    }
    public static Specification<Mission> hasDepartureLocation(String departureLocation) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("departureLocation")), "%" + departureLocation.toLowerCase() + "%");
    }
    public static Specification<Mission> hasDepartureDate(LocalDate departureDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("departureDate"), departureDate);
    }
    public static Specification<Mission> hasArrivalDate(LocalDate arrivalDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("arrivalDate"), arrivalDate);
    }
    public static Specification<Mission> hasMissionType(String missionType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("missionType")), "%" + missionType.toLowerCase() + "%");
    }
    public static Specification<Mission> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    



}
