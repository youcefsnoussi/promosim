package com.promosim.gestionparc.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("vehicles", vehicles); // Add the list of vehicles to the model
        return "vehicles/list"; // Return the name of the view (e.g., list.html)
    }




    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("vehicle", new Vehicle()); // Add it to the model
        return "vehicles/create"; // Return the name of the view (e.g., edit.html)
    }

    @PostMapping("")
    public String createVehicle(@Valid @ModelAttribute("vehicle") Vehicle vehicle, BindingResult result) {
        if(result.hasErrors()) {
            return "vehicles/create"; // Return to the form view if there are validation errors
        }
        vehicleService.createVehicle(vehicle);
        return "redirect:/vehicles"; // Redirect to the list of vehicles after creation
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

    return "vehicles/list";
}

    

    @GetMapping("/edit/{vin}")
    public String showEditForm(@PathVariable("vin") String vin, Model model) {
        Vehicle vehicle = vehicleService.getVehicleByVin(vin); // Assuming you have a method to get a vehicle by VIN
        model.addAttribute("vehicle", vehicle);
        return "vehicles/edit"; // Return the name of the view (e.g., edit.html)
        
    }    


    @PostMapping("/update")
    public String updateVehicle(@Valid @ModelAttribute("vehicle") Vehicle vehicle, BindingResult result) {
        if(result.hasErrors()) {
            return "vehicles/edit"; // Return to the form view if there are validation errors
        }
        vehicleService.updateVehicle(vehicle); // Assuming createVehicle also handles updates
        return "redirect:/vehicles"; // Redirect to the list of vehicles after update
    }

    @PostMapping("/delete/{vin}")
    public String deleteVehicleByVin(@PathVariable("vin") String vin, Model model) {
            System.out.println("DELETING VIN: " + vin); // log to console
            vehicleService.deleteVehicleByVin(vin);
            List<Vehicle> vehicles = vehicleService.getAllVehicles(); // Fetch the updated list of vehicles
            model.addAttribute("vehicles", vehicles); // Update the model with the new list
            return "redirect:/vehicles"; // Redirect to the list of vehicles after deletion
        }
    }
    

