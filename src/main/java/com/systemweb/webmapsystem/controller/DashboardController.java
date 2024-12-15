package com.systemweb.webmapsystem.controller;

import com.systemweb.webmapsystem.model.Resident;
import com.systemweb.webmapsystem.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final ResidentRepository residentRepository;

    @Autowired
    public DashboardController(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @GetMapping("/Dashboard")
    public String getDashboard(Model model) {
        // Get all residents and separate deceased from non-deceased
        List<Resident> allResidents = residentRepository.findAll();
        List<Resident> livingResidents = allResidents.stream()
            .filter(resident -> !resident.getDeceased())
            .toList();
        
        // Calculate stats for living residents only
        long totalPopulation = livingResidents.size();
        long seniorCitizens = livingResidents.stream()
            .filter(resident -> resident.getAge() >= 60)
            .count();
        long maleCount = livingResidents.stream()
            .filter(resident -> "Male".equalsIgnoreCase(resident.getGender()))
            .count();
        long femaleCount = livingResidents.stream()
            .filter(resident -> "Female".equalsIgnoreCase(resident.getGender()))
            .count();
        long voters = livingResidents.stream()
            .filter(resident -> "Registered".equalsIgnoreCase(resident.getVotingEligibility()))
            .count();
        long nonVoters = totalPopulation - voters;
        long pwdCount = livingResidents.stream()
            .filter(Resident::getPwd)
            .count();
        
        // Calculate deceased count separately
        long deceasedCount = allResidents.stream()
            .filter(Resident::getDeceased)
            .count();
    
        // Add attributes to the model
        model.addAttribute("totalPopulation", totalPopulation);
        model.addAttribute("seniorCitizens", seniorCitizens);
        model.addAttribute("maleCount", maleCount);
        model.addAttribute("femaleCount", femaleCount);
        model.addAttribute("voters", voters);
        model.addAttribute("nonVoters", nonVoters);
        model.addAttribute("residents", livingResidents);
        model.addAttribute("pwdCount", pwdCount);
        model.addAttribute("deceasedCount", deceasedCount);
    
        return "Dashboard";  
    }
}
