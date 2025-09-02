package com.promosim.gestionparc.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.promosim.gestionparc.enums.MissionStatus;
import com.promosim.gestionparc.model.Driver;
import com.promosim.gestionparc.model.Mission;
import com.promosim.gestionparc.model.Vehicle;
import com.promosim.gestionparc.repository.MissionRepository;
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

    @Autowired
    private MissionRepository missionRepository;

    @GetMapping("/gestion")
    public String showGestionPage(Model model) {
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("drivers", driverService.getAllDrivers());
        model.addAttribute("missions", missionService.getAllMissions());
        return "gestion";
    }

    @GetMapping("")
    public String listMissions(Model model) {
        List<Mission> missions = missionService.getAllMissions(); // Fetch all missions
        model.addAttribute("missions", missions); // Add missions to the model
        return "gestion"; // Return the name of the view (e.g., list.html)
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
        return "redirect:/gestion";
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
        return "redirect:/gestion";
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
        @RequestParam (required = false) String status,
        Model model) {
        List<Mission> missions = missionService.searchMissions(id, name, vehicle, driver, destination, departureLocation,
                departureDate, arrivalDate, missionType, status);
        model.addAttribute("missions", missions);
        model.addAttribute("activeTab", "missions");
        return "gestion"; // Return the name of the view (e.g., list.html)

        }



    
    @GetMapping("/{id}/delete-confirmation")
    public String getDeleteConfirmationModal(@PathVariable Long id, Model model) {
        Mission mission = missionService.getMissionById(id); // or throw if not found
        if (mission == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mission not found");
        }
        boolean isOngoing = mission.getStatus() == MissionStatus.ONGOING;
        model.addAttribute("mission", mission);
        model.addAttribute("isOngoing", isOngoing); 
        return "fragments/modal-content-mission :: missionList";
    }
    @PostMapping("/delete/{id}")
    public String deleteMissionById(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    System.out.println("🚨 Controller hit: trying to delete Mission with ID = " + id);

    missionService.deleteMissionById(id);
    redirectAttributes.addFlashAttribute("successMessage", "Mission supprimée avec succès.");
    return "redirect:/gestion";
    }


   @PostMapping("/{id}/complete")
   @ResponseBody
   public ResponseEntity<?> markAsDone(@PathVariable Long id){
    Mission mission = missionRepository.findById(id).orElseThrow(() -> new RuntimeException("Mission not found"));
    mission.setStatus(MissionStatus.COMPLETED);
    missionService.updateMission(mission);

    return ResponseEntity.ok(Map.of("id", mission.getId(), "status", mission.getStatus().name()));
   }

    

}

    
