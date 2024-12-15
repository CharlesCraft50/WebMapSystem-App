package com.systemweb.webmapsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.systemweb.webmapsystem.model.Business;
import com.systemweb.webmapsystem.model.Establishment;
import com.systemweb.webmapsystem.service.BusinessService;
import com.systemweb.webmapsystem.web.EstablishmentDTO;

import java.util.List;

@RestController
@RequestMapping("/api/businesses")
public class BusinessAPIController {

    private final BusinessService businessService;

    @Autowired
    public BusinessAPIController(BusinessService businessService) {
        this.businessService = businessService;
    }

    // Endpoint to search businesses based on query
    @GetMapping("/search")
    public List<Business> searchBusinesses(@RequestParam String query) {
        System.out.println("Search query: " + query);
        return businessService.searchBusinesses(query);
    }

    @PostMapping("/add")  // Matches '/api/businesses/add'
    public ResponseEntity<Business> addBusiness(@RequestBody Business business) {
        try {
            // Validate input
            if (business.getName() == null || business.getLat() == null || business.getLng() == null) {
                return ResponseEntity.badRequest().build();
            }

            Business savedBusiness = businessService.saveBusiness(business);
            return ResponseEntity.ok(savedBusiness);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Business>> getAllBusinesses() {
        return ResponseEntity.ok(businessService.getAllBusinesses());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBusiness(@PathVariable Long id) {
        if (businessService.deleteBusiness(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}


