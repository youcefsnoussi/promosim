package com.promosim.gestionparc.specification;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.promosim.gestionparc.SearchCriteria.MissionSearchCriteria;
import com.promosim.gestionparc.model.Driver;
import com.promosim.gestionparc.model.Mission;
import com.promosim.gestionparc.model.Vehicle;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class MissionSpecifications {

    public static Specification<Mission> build(MissionSearchCriteria criteria) {
        return Specification
                .where(hasMissionType(criteria.getMissionType()))
                .and(hasDepartureLocation(criteria.getDepartureLocation()))
                .and(hasDestination(criteria.getDestination()))
                .and(hasDepartureDate(criteria.getDepartureDate()))
                .and(hasArrivalDate(criteria.getArrivalDate()))
                .and(hasVehiclePlateNumber(criteria.getVehiclePlateNumber()))
                .and(hasVehicleVin(criteria.getVehicleVin()))
                .and(hasVehicleBrand(criteria.getVehicleBrand()))
                .and(hasVehicleModel(criteria.getVehicleModel()))
                .and(hasDriverLicenseNumber(criteria.getDriverLicenseNumber()))
                .and(hasDriverFirstName(criteria.getDriverFirstName()))
                .and(hasDriverLastName(criteria.getDriverLastName()))
                .and(isDone(criteria.getDone()));
    }

    public static Specification<Mission> hasMissionType(String missionType) {
        return (root, query, cb) ->
                (missionType == null || missionType.isEmpty())
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("missionType")), "%" + missionType.toLowerCase() + "%");
    }

    public static Specification<Mission> hasDepartureLocation(String departureLocation) {
        return (root, query, cb) ->
                (departureLocation == null || departureLocation.isEmpty())
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("departureLocation")), "%" + departureLocation.toLowerCase() + "%");
    }

    public static Specification<Mission> hasDestination(String destination) {
        return (root, query, cb) ->
                (destination == null || destination.isEmpty())
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("destination")), "%" + destination.toLowerCase() + "%");
    }

    public static Specification<Mission> hasDepartureDate(LocalDate departureDate) {
        return (root, query, cb) ->
                (departureDate == null)
                        ? cb.conjunction()
                        : cb.equal(root.get("departureDate"), departureDate);
    }

    public static Specification<Mission> hasArrivalDate(LocalDate arrivalDate) {
        return (root, query, cb) ->
                (arrivalDate == null)
                        ? cb.conjunction()
                        : cb.equal(root.get("arrivalDate"), arrivalDate);
    }

    public static Specification<Mission> hasVehiclePlateNumber(String plateNumber) {
        return (root, query, cb) -> {
            if (plateNumber == null || plateNumber.isEmpty()) return cb.conjunction();
            Join<Mission, Vehicle> vehicleJoin = root.join("vehicle", JoinType.LEFT);
            return cb.like(cb.lower(vehicleJoin.get("plateNumber")), "%" + plateNumber.toLowerCase() + "%");
        };
    }

    public static Specification<Mission> hasVehicleVin(String vin) {
        return (root, query, cb) -> {
            if (vin == null || vin.isEmpty()) return cb.conjunction();
            Join<Mission, Vehicle> vehicleJoin = root.join("vehicle", JoinType.LEFT);
            return cb.like(cb.lower(vehicleJoin.get("vin")), "%" + vin.toLowerCase() + "%");
        };
    }

    public static Specification<Mission> hasVehicleBrand(String brand) {
        return (root, query, cb) -> {
            if (brand == null || brand.isEmpty()) return cb.conjunction();
            Join<Mission, Vehicle> vehicleJoin = root.join("vehicle", JoinType.LEFT);
            return cb.like(cb.lower(vehicleJoin.get("brand")), "%" + brand.toLowerCase() + "%");
        };
    }

    public static Specification<Mission> hasVehicleModel(String model) {
        return (root, query, cb) -> {
            if (model == null || model.isEmpty()) return cb.conjunction();
            Join<Mission, Vehicle> vehicleJoin = root.join("vehicle", JoinType.LEFT);
            return cb.like(cb.lower(vehicleJoin.get("model")), "%" + model.toLowerCase() + "%");
        };
    }

    public static Specification<Mission> hasDriverLicenseNumber(String licenseNumber) {
        return (root, query, cb) -> {
            if (licenseNumber == null || licenseNumber.isEmpty()) return cb.conjunction();
            Join<Mission, Driver> driverJoin = root.join("driver", JoinType.LEFT);
            return cb.like(cb.lower(driverJoin.get("licenseNumber")), "%" + licenseNumber.toLowerCase() + "%");
        };
    }

    public static Specification<Mission> hasDriverFirstName(String firstName) {
        return (root, query, cb) -> {
            if (firstName == null || firstName.isEmpty()) return cb.conjunction();
            Join<Mission, Driver> driverJoin = root.join("driver", JoinType.LEFT);
            return cb.like(cb.lower(driverJoin.get("firstName")), "%" + firstName.toLowerCase() + "%");
        };
    }

    public static Specification<Mission> hasDriverLastName(String lastName) {
        return (root, query, cb) -> {
            if (lastName == null || lastName.isEmpty()) return cb.conjunction();
            Join<Mission, Driver> driverJoin = root.join("driver", JoinType.LEFT);
            return cb.like(cb.lower(driverJoin.get("lastName")), "%" + lastName.toLowerCase() + "%");
        };
    }

    public static Specification<Mission> isDone(Boolean done) {
        return (root, query, cb) ->
                (done == null)
                        ? cb.conjunction()
                        : cb.equal(root.get("done"), done);
    }
}
