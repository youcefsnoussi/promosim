package com.promosim.gestionparc.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.promosim.gestionparc.model.Driver;
import com.promosim.gestionparc.model.Mission;
import com.promosim.gestionparc.service.DriverService;
import com.promosim.gestionparc.service.MissionService;
import com.promosim.gestionparc.service.VehicleService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private MissionService missionService;

    @GetMapping("/gestion")
public String showGestionPage(Model model) {
    model.addAttribute("vehicles", vehicleService.getAllVehicles());
    model.addAttribute("drivers", driverService.getAllDrivers());
    model.addAttribute("missions", missionService.getAllMissions());
    return "gestion";
}


    @GetMapping
    public String listDrivers(Model model){
        List<Driver> drivers = driverService.getAllDrivers();
        model.addAttribute("drivers", drivers);
        return "gestion"; // Return the name of the view to render (e.g., Thymeleaf template)
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){
        Driver driver = new Driver();
        model.addAttribute("driver", driver);
        return "drivers/create"; // Return the name of the view to render (e.g., Thymeleaf template)
    }

    @PostMapping("")
    public String createDriver(@Valid @ModelAttribute Driver driver, BindingResult result) {
        System.out.println("Creating driver: " + driver);
        if(result.hasErrors()) {
            return "drivers/create"; // Return to the form view if there are validation errors
        }
        driverService.createDriver(driver);
        return "redirect:/gestion";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Driver driver = driverService.getDriverById(id);
        model.addAttribute("driver", driver);
        return "drivers/edit"; // Return the name of the view (e.g., edit.html) 
    }  
    @PostMapping("/edit/{id}")
    public String updateDriver(@Valid @ModelAttribute Driver driver, BindingResult result) {
        if(result.hasErrors()) {
            return "drivers/edit"; // Return to the form view if there are validation errors
        }
        driverService.updateDriver(driver); 
        return "redirect:/gestion"; 
    }

    @GetMapping("/search")
    public String searchDrivers(
        @RequestParam(required = false) Long id,
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String lastName,
        @RequestParam(required = false) String phoneNumber,
        @RequestParam(required = false) String licenseNumber,
        @RequestParam(required = false) String homeAddress,
        @RequestParam(required = false) LocalDate dateOfBirth,
        Model model) {
        List<Driver> drivers = driverService.searchDrivers(id, firstName, lastName, phoneNumber, licenseNumber, homeAddress, dateOfBirth);
        model.addAttribute("drivers", drivers);
        model.addAttribute("activeTab", "drivers");
        return "gestion"; // Return the name of the view to render (e.g., Thymeleaf template)
    }

    @GetMapping("/{id}/delete-confirmation")
    public String getDeleteConfirmationModal(@PathVariable Long id, Model model){
        Driver driver = driverService.getDriverById(id);
        if (driver == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Driver not found");
        }
        List<Mission> missions = missionService.getActiveMissionsByDriverId(id);
        model.addAttribute("missions", missions);
        model.addAttribute("driverId", id);
        return "fragments/modal-content-driver :: missionList";
    }




    @PostMapping("/delete/{id}")
    public String deleteDriver(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (driverService.isDriverAssignedToAnyMission(id)) {
            redirectAttributes.addFlashAttribute("error", "Le conducteur est affecté à une mission et ne peut pas être supprimé.");
            return "redirect:/drivers"; // Redirect to the list of drivers with an error message
        }
        driverService.deleteDriverById(id);
        redirectAttributes.addFlashAttribute("success", "Conducteur supprimé avec succès.");
        return "redirect:/gestion";
    }
}
