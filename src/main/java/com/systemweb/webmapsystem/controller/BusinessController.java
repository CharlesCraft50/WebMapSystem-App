package com.systemweb.webmapsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.systemweb.webmapsystem.model.Business;
import com.systemweb.webmapsystem.repository.BusinessRepository;

@Controller
public class BusinessController {

    @Autowired
    private BusinessRepository businessRepository;

    // Method to show all businesses
    @GetMapping("/BusinessRecords")
    public String listBusinesses(Model model) {
        model.addAttribute("Business", businessRepository.findAll());
        return "BusinessRecords"; // This should match the template file name (businesses.html)
    }

    // Method to handle adding a business
    @GetMapping("/add-business")
    public String showAddBusinessForm() {
        return "add-business-form"; // Thymeleaf template with the form to add a business
    }

    @PostMapping("/add-business")
    public String addBusiness(@RequestParam String name,
                               @RequestParam String owner,
                               @RequestParam String address,
                               @RequestParam String startDate) {
        Business business = new Business();
        business.setName(name);
        business.setOwner(owner);
        business.setAddress(address);
        business.setStartDate(startDate);

        businessRepository.save(business);
        return "redirect:/BusinessRecords"; // Redirect to the list of businesses
    }
}

