package com.systemweb.webmapsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systemweb.webmapsystem.model.Business;
import com.systemweb.webmapsystem.repository.BusinessRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessService {

    private final BusinessRepository businessRepository;

    @Autowired
    public BusinessService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    // Method to fetch all businesses
    public List<Business> getAllBusinesses() {
        return businessRepository.findAll();
    }

    public Business saveBusiness(Business business) {
        return businessRepository.save(business);
    }

    // Method to search businesses based on the query
    public List<Business> searchBusinesses(String query) {
        List<Business> allBusinesses = businessRepository.findAll();
        
        // Filter businesses by name, owner, or address
        return allBusinesses.stream()
                .filter(business -> business.getName().toLowerCase().contains(query.toLowerCase()) ||
                        business.getOwner().toLowerCase().contains(query.toLowerCase()) ||
                        business.getAddress().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public boolean deleteBusiness(Long id) {
        if (businessRepository.existsById(id)) {
            businessRepository.deleteById(id);  // Delete business by ID
            return true;
        } else {
            return false;  // If business doesn't exist
        }
    }
}

