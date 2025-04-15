package com.promosim.gestionparc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.promosim.gestionparc.SearchCriteria.MissionSearchCriteria;
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

    public List<Mission> searchMissions(MissionSearchCriteria criteria) {
        Specification<Mission> spec = MissionSpecifications.build(criteria);
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
