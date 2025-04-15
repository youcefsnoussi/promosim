package com.promosim.gestionparc.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.promosim.gestionparc.model.Driver;

public class DriverSpecifications {

    public static Specification<Driver> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Driver> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<Driver> hasLastName(String lastName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }
    public static Specification<Driver> hasPhoneNumber(String phoneNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), "%" + phoneNumber.toLowerCase() + "%");
    }

    public static Specification<Driver> hasLicenseNumber(String licenseNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("licenseNumber")), "%" + licenseNumber.toLowerCase() + "%");
    }
    public static Specification<Driver> hasHomeAddress(String homeAddress) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("homeAddress")), "%" + homeAddress.toLowerCase() + "%");
    }
    public static Specification<Driver> hasDateOfBirth(LocalDate dateOfBirth) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dateOfBirth"), dateOfBirth);
    }
}
