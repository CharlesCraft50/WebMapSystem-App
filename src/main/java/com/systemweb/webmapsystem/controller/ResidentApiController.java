// File: com/systemweb/webmapsystem/controller/ResidentApiController.java

package com.systemweb.webmapsystem.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.systemweb.webmapsystem.model.House;
import com.systemweb.webmapsystem.model.Resident;
import com.systemweb.webmapsystem.repository.HouseRepository;
import com.systemweb.webmapsystem.repository.ResidentRepository;
import com.systemweb.webmapsystem.service.ResidentService;
import com.systemweb.webmapsystem.web.HouseDTO;
import com.systemweb.webmapsystem.web.HouseWithResidentsDTO;
import com.systemweb.webmapsystem.web.ResidentDTO;

@RestController
@RequestMapping("/api/residents")
public class ResidentApiController {

    @Autowired
    private ResidentService residentService;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private ResidentRepository residentRepository;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getResidents(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<Resident> residents;

            if (category != null && category.equals("Deceased")) {
                residents = residentRepository.findByIsDeceasedTrue();
            } else if (category != null) {
                residents = switch (category) {
                    case "Male" -> residentRepository.findByGender("Male");
                    case "Female" -> residentRepository.findByGender("Female");
                    case "Voters" -> residentRepository.findByVotingEligibility("Registered");
                    case "Non Voters" -> residentRepository.findByVotingEligibility("Non Registered");
                    case "Senior Citizens" -> residentRepository.findByAgeGreaterThanEqual(60);
                    case "PWD" -> residentRepository.findByPwdTrue();
                    case "Deceased" -> residentRepository.findByIsDeceasedTrue();
                    default -> residentRepository.findAll(); // Total Population
                };
            } else {
                Page<Resident> residentPage = residentService.findResidents(query, age, page, size);
                residents = residentPage.getContent();
            }

            List<Map<String, Object>> result = residents.stream().map(resident -> {
                Map<String, Object> resMap = new HashMap<>();
                resMap.put("id", resident.getId());
                resMap.put("firstName", resident.getFirstName());
                resMap.put("lastName", resident.getLastName());
                resMap.put("age", resident.getAge());
                resMap.put("gender", resident.getGender());
                resMap.put("birthDate", resident.getBirthDate());
                resMap.put("nationality", resident.getNationality());
                resMap.put("votingEligibility", resident.getVotingEligibility());
                resMap.put("isDeceased", resident.getDeceased());
                return resMap;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }
     
    
    @PostMapping
    public ResponseEntity<String> saveResident(
            @RequestParam("photoInput") MultipartFile photoFile,
            @ModelAttribute Resident resident) {
        try {
            if (!photoFile.isEmpty()) {
                resident.setPhoto(photoFile.getBytes());
            }
            residentService.saveResident(resident);
            return ResponseEntity.ok("Resident saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to save resident.");
        }
    }   

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable Long id) {
        Optional<Resident> resident = residentRepository.findById(id);
        if (resident.isPresent()) {
            residentRepository.delete(resident.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resident> getResident(@PathVariable Long id) {
        Optional<Resident> residentOpt = residentService.findById(id);
        return residentOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/house/{residentId}")
    public ResponseEntity<HouseWithResidentsDTO> getHouseWithResidents(@PathVariable Long residentId) {
        List<House> houses = houseRepository.findAll(); // Fetch all houses to search for the matching resident
    
        // Find the house that contains the given resident ID
        House matchingHouse = houses.stream()
                                    .filter(house -> house.getResidentIds().contains(residentId))
                                    .findFirst()
                                    .orElse(null);
    
        if (matchingHouse == null) {
            return ResponseEntity.notFound().build();
        }
    
        // Prepare the response
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setId(matchingHouse.getId());
        houseDTO.setHouseNumber(matchingHouse.getHouseNumber());
        houseDTO.setAddress(matchingHouse.getAddress());
        houseDTO.setLat(matchingHouse.getLat());
        houseDTO.setLng(matchingHouse.getLng());
        houseDTO.setResidentIds(matchingHouse.getResidentIds());
    
        // Map residents to DTOs
        List<ResidentDTO> residentDTOs = matchingHouse.getResidents().stream().map(resident -> {
            ResidentDTO residentDTO = new ResidentDTO();
            residentDTO.setId(resident.getId());
            residentDTO.setFirstName(resident.getFirstName());
            residentDTO.setLastName(resident.getLastName());
            return residentDTO;
        }).collect(Collectors.toList());
    
        return ResponseEntity.ok(new HouseWithResidentsDTO(houseDTO, residentDTOs));
    }
    
    

    @PostMapping("/{id}/increment-certificate")
    public ResponseEntity<Map<String, Object>> incrementCertificateCount(
            @PathVariable Long id,
            @RequestParam String certificateType) {
        try {
            Resident updatedResident = residentService.incrementCertificateCount(id, certificateType);
            Map<String, Object> response = new HashMap<>();
            response.put("id", updatedResident.getId());
            response.put("certificateCount", switch (certificateType.toUpperCase()) {
                case "BARANGAY-INDIGENCY" -> updatedResident.getCertificateOfIndigencyCount();
                case "CLEARANCE" -> updatedResident.getClearanceCount();
                case "FIRST-TIME-JOB-SEEKER" -> updatedResident.getFirstTimeJobSeekerCount();
                default -> throw new IllegalArgumentException("Invalid certificate type");
            });
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/with-counts")
    public ResponseEntity<Map<String, Object>> getResidentsWithCounts() {
        try {
            List<Resident> residents = residentRepository.findAll();  

            List<Map<String, Object>> result = new ArrayList<>();
            for (Resident resident : residents) {
                Map<String, Object> resMap = new HashMap<>();
                resMap.put("id", resident.getId());
                resMap.put("firstName", resident.getFirstName());
                resMap.put("lastName", resident.getLastName());
                resMap.put("age", resident.getAge());
                resMap.put("gender", resident.getGender());
                resMap.put("birthDate", resident.getBirthDate());
                resMap.put("nationality", resident.getNationality());
                resMap.put("votingEligibility", resident.getVotingEligibility());
                resMap.put("isDeceased", resident.getDeceased());
                
                // Add certificate counts for each resident
                resMap.put("certificateOfIndigencyCount", resident.getCertificateOfIndigencyCount());
                resMap.put("barangayClearanceCount", resident.getClearanceCount());
                resMap.put("firstTimeJobSeekerCount", resident.getFirstTimeJobSeekerCount());

                result.add(resMap);
            }

            return ResponseEntity.ok(Collections.singletonMap("residents", result));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Collections.emptyMap());
        }
    }

    @GetMapping("/viewer/{id}")
    public ResponseEntity<Resident> getResidentById(@PathVariable Long id) {
        Optional<Resident> resident = residentRepository.findById(id);
        return resident.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PatchMapping("/{id}/markDeceased")
    public ResponseEntity<?> markResidentDeceased(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        Resident resident = residentRepository.findById(id).orElse(null);
    
        // Check if resident is not found and return a 404 response
        if (resident == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resident not found");
        }
    
        resident.setDeceased(body.get("isDeceased"));
        residentRepository.save(resident);
    
        return ResponseEntity.ok().build();
    }
    
    // Add to ResidentController.java
    @GetMapping("/{residentId}/houses")
    public ResponseEntity<List<House>> getHousesForResident(@PathVariable Long residentId) {
        try {
            List<House> houses = residentService.getHousesForResident(residentId);
            return ResponseEntity.ok(houses);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    



}
