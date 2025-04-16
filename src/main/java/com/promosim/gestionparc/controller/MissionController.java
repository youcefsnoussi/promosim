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
    public String listMissions(Model model) {
        List<Mission> missions = missionService.getAllMissions(); // Fetch all missions
        model.addAttribute("missions", missions); // Add missions to the model
        return "missions/list"; // Return the name of the view (e.g., list.html)
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("mission", new Mission());
        model.addAttribute("vehicles", vehicleService.getAvailableVehicles());
        model.addAttribute("drivers", driverService.getAvailableDrivers());
        return "missions/create";
    }
    @PostMapping("/create")
    public String createMission(@ModelAttribute Mission mission){
        missionService.createMission(mission);
        return "redirect:/missions";
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
        @RequestParam (required = false) Long id,
        @RequestParam (required = false) String name,
        @RequestParam (required = false) String vehicle,
        @RequestParam (required = false) String driver,
        @RequestParam (required = false) String destination,
        @RequestParam (required = false) String departureLocation,
        @RequestParam (required = false) LocalDate departureDate,
        @RequestParam (required = false) LocalDate arrivalDate,
        @RequestParam (required = false) String missionType,
        @RequestParam (required = false) boolean done,
        Model model) {
        List<Mission> missions = missionService.searchMissions(id, name, vehicle, driver, destination, departureLocation,
                departureDate, arrivalDate, missionType, done);
        model.addAttribute("missions", missions);
        return "missions/list"; // Return the name of the view (e.g., list.html)

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

    
