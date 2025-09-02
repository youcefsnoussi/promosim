package com.promosim.gestionparc.controller;
import com.promosim.gestionparc.service.DriverService;
import com.promosim.gestionparc.service.MissionService;
import com.promosim.gestionparc.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        System.out.println(">>> /gestion route hit");
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("drivers", driverService.getAllDrivers());
        model.addAttribute("missions", missionService.getAllMissions());
        model.addAttribute("username", username);
        model.addAttribute("isAdmin", isAdmin);
        return "gestion";  // Your gestion.html page
    }
}
