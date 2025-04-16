package com.promosim.gestionparc.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.promosim.gestionparc.model.Mission;
import com.promosim.gestionparc.repository.MissionRepository;
import com.promosim.gestionparc.specification.MissionSpecifications;

import jakarta.transaction.Transactional;

@Service
public class MissionService {

    @Autowired
    private MissionRepository missionRepository;

    public Mission createMission(Mission mission) {
        return missionRepository.save(mission);
    }

    public List<Mission> getAllMissions() {
        return missionRepository.findAll();
    }

    public List<Mission> searchMissions(Long id, String name, String vehicle, String driver, String destination,
            String departureLocation, LocalDate departureDate, LocalDate arrivalDate, String missionType, boolean done) {
        Specification<Mission> spec = Specification.where(null);

        if (id != null) {
            spec = spec.and(MissionSpecifications.hasId(id));
        }
        if (name != null && !name.isEmpty()) {
            spec = spec.and(MissionSpecifications.hasName(name.trim()));
        }
        if (vehicle != null && !vehicle.isEmpty()) {
            spec = spec.and(MissionSpecifications.hasVehicle(vehicle.trim()));
        }
        if (driver != null && !driver.isEmpty()) {
            spec = spec.and(MissionSpecifications.hasDriver(driver.trim()));
        }
        if (destination != null && !destination.isEmpty()) {
            spec = spec.and(MissionSpecifications.hasDestination(destination.trim()));
        }
        if (departureLocation != null && !departureLocation.isEmpty()) {
            spec = spec.and(MissionSpecifications.hasDepartureLocation(departureLocation.trim()));
        }
        if (departureDate != null) {
            spec = spec.and(MissionSpecifications.hasDepartureDate(departureDate));
        }
        if (arrivalDate != null) {
            spec = spec.and(MissionSpecifications.hasArrivalDate(arrivalDate));
        }
        if (missionType != null && !missionType.isEmpty()) {
            spec = spec.and(MissionSpecifications.hasMissionType(missionType.trim()));
        }
        if (done) {
            spec = spec.and(MissionSpecifications.isDone(done));
        }

        return missionRepository.findAll(spec);
       
    }

    public Mission getMissionById(Long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mission not found"));
    }

    @Transactional
    public Mission updateMission(Mission updatedMission) {
        return missionRepository.findById(updatedMission.getId())
                .map(existing -> {
                    existing.setName(updatedMission.getName());
                    existing.setVehicle(updatedMission.getVehicle());
                    existing.setDriver(updatedMission.getDriver());
                    existing.setDestination(updatedMission.getDestination());
                    existing.setDepartureLocation(updatedMission.getDepartureLocation());
                    existing.setDepartureDate(updatedMission.getDepartureDate());
                    existing.setArrivalDate(updatedMission.getArrivalDate());
                    existing.setMissionType(updatedMission.getMissionType());
                    existing.setDone(updatedMission.isDone());
                    existing.setTasks(updatedMission.getTasks());
                    return missionRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Mission not found"));
    }

    public void deleteMissionById(Long id) {
        missionRepository.deleteById(id);
    }
}
