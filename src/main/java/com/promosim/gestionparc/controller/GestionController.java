package com.promosim.gestionparc.controller;
import com.promosim.gestionparc.service.DriverService;
import com.promosim.gestionparc.service.MissionService;
import com.promosim.gestionparc.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/gestion")
public class GestionController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private MissionService missionService;

    @GetMapping("")
    public String gestionPage(Model model) {
        System.out.println(">>> /gestion route hit");
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("drivers", driverService.getAllDrivers());
        model.addAttribute("missions", missionService.getAllMissions());
        return "gestion";  // Your gestion.html page
    }
}
