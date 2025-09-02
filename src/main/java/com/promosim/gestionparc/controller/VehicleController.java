package com.promosim.gestionparc.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.promosim.gestionparc.model.Mission;
import com.promosim.gestionparc.model.Vehicle;
import com.promosim.gestionparc.service.DriverService;
import com.promosim.gestionparc.service.MissionService;
import com.promosim.gestionparc.service.VehicleService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/vehicles")
@Validated
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private DriverService driverService;
    @Autowired
    private MissionService missionService;

    @GetMapping("/gestion")
public String showGestionPage(Model model) {
    model.addAttribute("vehicles", vehicleService.getAllVehicles());
    model.addAttribute("drivers", driverService.getAllDrivers());
    model.addAttribute("missions", missionService.getAllMissions());
    return "gestion";
}


@GetMapping("")
public String listVehicles(Model model) {
    List<Vehicle> vehicles = vehicleService.getAllVehicles(); // Fetch all vehicles
    model.addAttribute("vehicles", vehicles); // Add vehicles to the model
    return "gestion"; // Return the name of the view (e.g., list.html)
}



    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("vehicle", new Vehicle()); // Add it to the model
        return "vehicles/create"; // Return the name of the view (e.g., edit.html)
    }

    @PostMapping("")
    public String createVehicle(@Valid @ModelAttribute Vehicle vehicle, BindingResult result) {
        System.out.println("Creating vehicle: " + vehicle);
        if(result.hasErrors()) {
            return "vehicles/create"; // Return to the form view if there are validation errors
        }
        vehicleService.createVehicle(vehicle);
        return "redirect:/gestion"; // Redirect to the list of vehicles after creation
    }
    

    @GetMapping("/search")
public String searchVehicles(
    @RequestParam(required = false) Long id,
    @RequestParam(required = false) String brand,
    @RequestParam(required = false) String model,
    @RequestParam(required = false) String plateNumber,
    @RequestParam(required = false) String type,
    @RequestParam(required = false) String fuelType,
    @RequestParam(required = false) String vin,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastServiceDate,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate nextServiceDate,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate insuranceExpiryDate,
    @RequestParam(required = false) String address,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate nextMaintenanceDate,
    @RequestParam(required = false) String maintenanceType,
    Model modelAttr) {

    List<Vehicle> vehicles = vehicleService.searchVehicles(id, brand, model, plateNumber, type, fuelType, vin,
            lastServiceDate, nextServiceDate, insuranceExpiryDate, address, nextMaintenanceDate, maintenanceType);

    modelAttr.addAttribute("vehicles", vehicles);
    modelAttr.addAttribute("activeTab", "vehicles"); // Set the active tab to "vehicles"
    return "gestion";
}

    

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        model.addAttribute("vehicle", vehicle);
        return "vehicles/edit"; // Return the name of the view (e.g., edit.html) 
    }    


    @PostMapping("/edit/{id}")
    public String updateVehicle(@Valid @ModelAttribute Vehicle vehicle, BindingResult result) {
        if(result.hasErrors()) {
            return "vehicles/edit"; // Return to the form view if there are validation errors
        }
        vehicleService.updateVehicle(vehicle); // Assuming createVehicle also handles updates
        return "redirect:/gestion"; // Redirect to the list of vehicles after update
    }


    @GetMapping("/{id}/delete-confirmation")
    public String getDeleteConfirmationModal(@PathVariable Long id, Model model) {
        Vehicle vehicle = vehicleService.getVehicleById(id); // or throw if not found
        if (vehicle == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
        List<Mission> missions = missionService.getActiveMissionsByVehicleId(id);
    
        model.addAttribute("missions", missions);
        model.addAttribute("vehicleId", id); // needed for delete button in modal
    
        return "fragments/modal-content :: missionList";
    }





    @PostMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    System.out.println("🚨 Controller hit: trying to delete vehicle with ID = " + id);

    if (vehicleService.isVehicleAssignedToAnyMission(id)) {
        System.out.println("⚠️ Vehicle is assigned to a mission and cannot be deleted.");
        redirectAttributes.addFlashAttribute("errorMessage", "Le véhicule est affecté à une mission et ne peut pas être supprimé.");
        return "redirect:/vehicles";
    }

    vehicleService.deleteVehicle(id);
    redirectAttributes.addFlashAttribute("successMessage", "Véhicule supprimé avec succès.");
    return "redirect:/gestion";
    }

    @GetMapping("/missions-assigned/{vehicleId}")
    public String getMissionsAssignedToVehicle(@PathVariable Long vehicleId, Model model) {
        List<Mission> missions = missionService.getActiveMissionsByVehicleId(vehicleId);
        model.addAttribute("missions", missions);
        return "vehicles/modal-content :: missionList";
    }

  


        






}
    

