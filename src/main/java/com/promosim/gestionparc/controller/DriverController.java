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
import com.promosim.gestionparc.service.DriverService;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping
    public String listDrivers(Model model){
        List<Driver> drivers = driverService.getAllDrivers();
        model.addAttribute("drivers", drivers);
        return "drivers/list"; // Return the name of the view to render (e.g., Thymeleaf template)
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){
        Driver driver = new Driver();
        model.addAttribute("driver", driver);
        return "drivers/create"; // Return the name of the view to render (e.g., Thymeleaf template)
    }

    @PostMapping("/create")
    public String createDriver(@ModelAttribute Driver driver){
        driverService.createDriver(driver);
        return "redirect:/drivers"; // Redirect to the list of drivers after creation
    }

    @GetMapping("edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        Driver driver = driverService.getAllDrivers().stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Driver not found."));
        model.addAttribute("driver", driver);
        return "drivers/edit"; // Return the name of the view to render (e.g., Thymeleaf template)
    }
    @PostMapping("/edit")
    public String updateDriver(@ModelAttribute Driver driver){
        driverService.updateDriver(driver);
        return "redirect:/drivers"; // Redirect to the list of drivers after update
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
        return "drivers/list"; // Return the name of the view to render (e.g., Thymeleaf template)
    }
    @GetMapping("/delete/{id}")
    public String deleteDriver(@PathVariable Long id){
        driverService.deleteDriverById(id);
        return "redirect:/drivers"; // Redirect to the list of drivers after deletion
    }
}
