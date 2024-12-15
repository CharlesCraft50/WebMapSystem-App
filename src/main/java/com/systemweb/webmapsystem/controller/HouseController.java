package com.systemweb.webmapsystem.controller;

import com.systemweb.webmapsystem.model.House;
import com.systemweb.webmapsystem.model.Resident;
import com.systemweb.webmapsystem.repository.HouseRepository;
import com.systemweb.webmapsystem.service.HouseService;
import com.systemweb.webmapsystem.web.HouseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/house")
public class HouseController {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseService houseService;

    @GetMapping("/all")
    public ResponseEntity<List<House>> getAllHouses() {
        List<House> houses = houseService.getAllHouses();  
        if (houses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(houses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseDTO> getHouseById(@PathVariable Long id) {
        return houseRepository.findById(id)
                .map(house -> new HouseDTO(
                        house.getId(),
                        house.getAddress(),
                        house.getHouseNumber(),
                        house.getName(),
                        house.getLat(),
                        house.getLng(),
                        house.getResidentIds()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HouseDTO> updateHouse(@PathVariable Long id, @RequestBody HouseDTO houseDTO) {
        return houseRepository.findById(id)
                .map(existingHouse -> {
                    existingHouse.setName(houseDTO.getName());
                    existingHouse.setLat(houseDTO.getLat());
                    existingHouse.setLng(houseDTO.getLng());
                    
                    houseRepository.save(existingHouse);
                    
                   
                    return ResponseEntity.ok(new HouseDTO(
                        existingHouse.getId(),
                        existingHouse.getAddress(),
                        existingHouse.getHouseNumber(),
                        existingHouse.getName(),
                        existingHouse.getLat(),
                        existingHouse.getLng(),
                        existingHouse.getResidentIds()));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/add")
    public ResponseEntity<HouseDTO> addHouse(@RequestBody House house) {
        if (house.getName() == null || house.getName().isEmpty()) {
             
            return ResponseEntity.badRequest().body(new HouseDTO());
        }
    
        House savedHouse = houseRepository.save(house);
    
        return ResponseEntity.ok(new HouseDTO(
                savedHouse.getId(),
                savedHouse.getAddress(),
                savedHouse.getHouseNumber(),
                savedHouse.getName(),
                savedHouse.getLat(),
                savedHouse.getLng(),
                savedHouse.getResidents().stream().map(Resident::getId).collect(Collectors.toList())
        ));
    }
    
    @PostMapping("/{houseId}/add-resident/{residentId}")
    public ResponseEntity<HouseDTO> addResidentToHouse(@PathVariable Long houseId, @PathVariable Long residentId) {
        return houseRepository.findById(houseId)
                .map(house -> {
                    house.getResidents().add(new Resident(residentId)); 
                    houseRepository.save(house);
                    return ResponseEntity.ok(new HouseDTO(
                            house.getId(),
                            house.getAddress(),
                            house.getHouseNumber(),
                            house.getName(),
                            house.getLat(),
                            house.getLng(),
                            house.getResidentIds()));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @DeleteMapping("/{houseId}/remove-resident/{residentId}")
    public ResponseEntity<HouseDTO> removeResidentFromHouse(@PathVariable Long houseId, @PathVariable Long residentId) {
        var houseOptional = houseRepository.findById(houseId);
        
        if (houseOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); 
        }
        
        House existingHouse = houseOptional.get();
        
        // Find the resident to remove
        Resident residentToRemove = existingHouse.getResidents().stream()
                .filter(resident -> resident.getId().equals(residentId))
                .findFirst()
                .orElse(null);
    
        if (residentToRemove == null) {
            return ResponseEntity.notFound().build();
        }
    
        // Remove the resident if found and save the house
        existingHouse.getResidents().remove(residentToRemove);
        houseRepository.save(existingHouse);
    
        // Build and return the updated HouseDTO
        HouseDTO updatedHouseDTO = new HouseDTO(
                existingHouse.getId(),
                existingHouse.getAddress(),
                existingHouse.getHouseNumber(),
                existingHouse.getName(),
                existingHouse.getLat(),
                existingHouse.getLng(),
                existingHouse.getResidentIds()
        );
    
        return ResponseEntity.ok(updatedHouseDTO);
    }
    
    
    
    
    

    
    
    @GetMapping("/{houseId}/residents")
    public List<Resident> getResidentsForHouse(@PathVariable Long houseId) {
        House house = houseService.findHouseById(houseId);
        return house.getResidents();  
    }

    @GetMapping("/view/{id}")
    public String getHouseById(@PathVariable("id") Long id, Model model) {
        House house = houseService.findHouseById(id);
        if (house != null) {
            model.addAttribute("house", house);
            model.addAttribute("residents", house.getResidents()); 
        } else {
            model.addAttribute("error", "House not found");
        }
        return "house-details";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable Long id) {
        return houseRepository.findById(id)
                .map(house -> {
                    houseRepository.delete(house);
                    return ResponseEntity.noContent().<Void>build();  
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
