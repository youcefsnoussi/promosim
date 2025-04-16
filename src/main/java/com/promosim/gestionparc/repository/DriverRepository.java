package com.promosim.gestionparc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.promosim.gestionparc.model.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long>, JpaSpecificationExecutor<Driver> {
    // Custom query methods can be defined here if needed

    List<Driver> findByIdNotIn(List<Long> ids);


}
