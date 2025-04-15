package com.promosim.gestionparc.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.promosim.gestionparc.SearchCriteria.MissionSearchCriteria;
import com.promosim.gestionparc.model.Driver;
import com.promosim.gestionparc.model.Mission;
import com.promosim.gestionparc.model.Vehicle;
import com.promosim.gestionparc.service.DriverService;
import com.promosim.gestionparc.service.MissionService;
import com.promosim.gestionparc.service.VehicleService;

@Controller
@RequestMapping("/missions")
public class MissionController {
    @Autowired
    private MissionService missionService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private DriverService driverService;

    @GetMapping
    public String listMissions(Model model){
        MissionSearchCriteria criteria = new MissionSearchCriteria();
        model.addAttribute("criteria", criteria);
        List<Mission> missions = missionService.getAllMissions();
        model.addAttribute("missions", missions);
        return "missions/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){
        Mission mission = new Mission();
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        List<Driver> drivers = driverService.getAllDrivers();
        model.addAttribute("mission", mission);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("drivers", drivers);
        return "missions/create";
    }
    @PostMapping("/create")
    public String createMission(@ModelAttribute Mission mission){
        missionService.createMission(mission);
        return "redirect:/missions";
    }
    @GetMapping("/select-vehicle")
public String selectVehicle(
    @RequestParam(required = false) String plateNumber,
    @RequestParam(required = false) String brand,
    @RequestParam(required = false) String model,
    @RequestParam(required = false) String type,
    @RequestParam(required = false) String fuelType,
    @RequestParam(required = false) String vin,
    @RequestParam(required = false) LocalDate lastServiceDate,
    @RequestParam(required = false) LocalDate nextServiceDate,
    @RequestParam(required = false) LocalDate insuranceExpiryDate,
    @RequestParam(required = false) String address,
    @RequestParam(required = false) LocalDate nextMaintenanceDate,
    @RequestParam(required = false) String maintenanceType,
    Model modelView
) {
    List<Vehicle> vehicles;

    // If no search parameters are provided, show all available vehicles
    if (plateNumber == null && brand == null && model == null && type == null && fuelType == null && vin == null && 
        lastServiceDate == null && nextServiceDate == null && insuranceExpiryDate == null && address == null && 
        nextMaintenanceDate == null && maintenanceType == null) {
        vehicles = vehicleService.getAvailableVehicles();
    } else {
        // Otherwise, filter based on the search criteria
        vehicles = vehicleService.searchVehicles(
            null, brand, model, plateNumber, type, fuelType, vin, lastServiceDate, nextServiceDate, 
            insuranceExpiryDate, address, nextMaintenanceDate, maintenanceType);
    }

    modelView.addAttribute("vehicles", vehicles);
    return "missions/select-vehicle";
}


@GetMapping("/select-driver")
public String selectDriver(Model model) {
    List<Driver> drivers = driverService.getAllDrivers(); // We'll define this method
    model.addAttribute("drivers", drivers);
    return "missions/select-driver"; // create this file next
}


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        Mission mission = missionService.getMissionById(id);
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        List<Driver> drivers = driverService.getAllDrivers();
        model.addAttribute("mission", mission);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("drivers", drivers);
        return "missions/edit";
    }

    @PostMapping("/edit")
    public String updateMission(@ModelAttribute Mission mission){
        missionService.updateMission(mission);
        return "redirect:/missions";
    }

    @GetMapping("/search")
    public String searchMissions(
        @ModelAttribute("criteria") MissionSearchCriteria searchCriteria,
        Model model){
        List<Mission> missions = missionService.searchMissions(searchCriteria);
        model.addAttribute("missions", missions);
        return "missions/list";
    }
    @GetMapping("/delete/{id}")
    public String deleteMission(@PathVariable Long id) {
        missionService.deleteMissionById(id);
        return "redirect:/missions";
    }

    @GetMapping("/done/{id}")
    public String markAsDone(@PathVariable Long id) {
        Mission mission = missionService.getMissionById(id);
        mission.setDone(true);
        missionService.updateMission(mission);
        return "redirect:/missions";
    }

}

    
