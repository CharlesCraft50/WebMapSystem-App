package com.systemweb.webmapsystem.controller;

import com.systemweb.webmapsystem.web.ResidentDTO;
import com.systemweb.webmapsystem.model.Resident;
import com.systemweb.webmapsystem.service.ResidentService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ResidentPageController {

    @Autowired
    private ResidentService residentService;
    
    @GetMapping("/NewResident")
    public String showNewResidentForm(@RequestParam(required = false) Long id, Model model) {
        ResidentDTO residentDTO = new ResidentDTO();
        if (id != null) {
            residentService.findById(id).ifPresent(resident -> {
                residentDTO.setId(resident.getId());
                residentDTO.setFirstName(resident.getFirstName());
                residentDTO.setMiddleName(resident.getMiddleName());
                residentDTO.setLastName(resident.getLastName());
                residentDTO.setSuffix(resident.getSuffix());
                residentDTO.setAge(resident.getAge());
                residentDTO.setGender(resident.getGender());
                residentDTO.setOccupation(resident.getOccupation());
                residentDTO.setMobileNo(resident.getMobileNo());
                residentDTO.setTelephoneNo(resident.getTelephoneNo());
                residentDTO.setBirthPlace(resident.getBirthPlace());        
                residentDTO.setPermanentAddress(resident.getPermanentAddress());
                residentDTO.setTemporaryAddress(resident.getTemporaryAddress());
                residentDTO.setNationality(resident.getNationality());
                residentDTO.setCivilStatus(resident.getCivilStatus());
                residentDTO.setBirthDate(resident.getBirthDate());
                residentDTO.setVotingEligibility(resident.getVotingEligibility());
                residentDTO.setLatitude(resident.getLatitude());
                residentDTO.setLongitude(resident.getLongitude());      
                residentDTO.setPwd(resident.getPwd());
                residentDTO.setDeceased(resident.getDeceased());          
            });
        }
        model.addAttribute("residentDTO", residentDTO);
        return "NewResident";
    }

    @PostMapping("/NewResident")
    public String submitForm(@ModelAttribute("residentDTO") ResidentDTO residentDTO, Model model) {
        try {
            Resident resident = new Resident();
            resident.setId(residentDTO.getId());
            resident.setFirstName(residentDTO.getFirstName());
            resident.setMiddleName(residentDTO.getMiddleName());
            resident.setLastName(residentDTO.getLastName());
            resident.setSuffix(residentDTO.getSuffix());
            resident.setAge(residentDTO.getAge());
            resident.setGender(residentDTO.getGender());
            resident.setOccupation(residentDTO.getOccupation());
            resident.setMobileNo(residentDTO.getMobileNo());
            resident.setTelephoneNo(residentDTO.getTelephoneNo());
            resident.setBirthPlace(residentDTO.getBirthPlace());        
            resident.setPermanentAddress(residentDTO.getPermanentAddress());
            resident.setTemporaryAddress(residentDTO.getTemporaryAddress());
            resident.setNationality(residentDTO.getNationality());
            resident.setCivilStatus(residentDTO.getCivilStatus());
            resident.setBirthDate(residentDTO.getBirthDate());
            resident.setVotingEligibility(residentDTO.getVotingEligibility());
            resident.setLatitude(residentDTO.getLatitude());
            resident.setLongitude(residentDTO.getLongitude());
            resident.setPwd(residentDTO.getPwd());
            resident.setDeceased(residentDTO.getDeceased());

            if (residentDTO.getPhotoInput() != null && !residentDTO.getPhotoInput().isEmpty()) {
                resident.setPhoto(residentDTO.getPhotoInput().getBytes());
            }

            if (resident.getId() != null) {
                residentService.updateResident(resident);
            } else {
                residentService.saveResident(resident);
            }

            model.addAttribute("message", "SAVED SUCCESSFULLY");
            return "redirect:/Records?success";
        } catch (IOException e) {
            model.addAttribute("message", "Failed to save resident.");
            return "NewResident";
        }
    }

    @GetMapping("/Records")
    public String showRecordsPage(Model model) {
        List<Resident> residents = residentService.getAllResidents();  
        model.addAttribute("residents", residents); 
        model.addAttribute("title", "Resident Records");
        return "Records";
    }
}
