package com.systemweb.webmapsystem.service;

import com.systemweb.webmapsystem.model.House;
import com.systemweb.webmapsystem.model.Resident;
import com.systemweb.webmapsystem.repository.HouseRepository;
import com.systemweb.webmapsystem.repository.ResidentRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResidentServiceImpl implements ResidentService {

    @Autowired
    private ResidentRepository residentRepository;
    
    private void updateResidentFields(Resident existingResident, Resident resident) {
        existingResident.setFirstName(resident.getFirstName());
        existingResident.setMiddleName(resident.getMiddleName());
        existingResident.setLastName(resident.getLastName());
        existingResident.setBirthDate(resident.getBirthDate());
        existingResident.setBirthPlace(resident.getBirthPlace());
        existingResident.setCivilStatus(resident.getCivilStatus());
        existingResident.setMobileNo(resident.getMobileNo());
        existingResident.setNationality(resident.getNationality());
        existingResident.setOccupation(resident.getOccupation());
        existingResident.setPhoto(resident.getPhoto());
        existingResident.setSuffix(resident.getSuffix());
        existingResident.setTelephoneNo(resident.getTelephoneNo());
        existingResident.setLatitude(resident.getLatitude());   
        existingResident.setLongitude(resident.getLongitude());
        existingResident.setVotingEligibility(resident.getVotingEligibility());
        existingResident.setPermanentAddress(resident.getPermanentAddress());
        existingResident.setTemporaryAddress(resident.getTemporaryAddress());
        existingResident.setPwd(resident.getPwd());
        existingResident.setDeceased(resident.getDeceased());

       
    }

    private int calculateAge(String birthDateString) {
        if (birthDateString == null || birthDateString.isEmpty()) return 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    } 

    @Override
    public List<Resident> getAllResidents() {
        List<Resident> residents = residentRepository.findAll();
        residents.forEach(resident -> resident.setAge(calculateAge(resident.getBirthDate())));
        return residents;
    }

    @Override
    public Optional<Resident> findById(Long id) {
        return residentRepository.findById(id);
    }

    @Override
    public Page<Resident> findResidents(String query, Integer age, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Resident> residentsPage = query == null && age == null ?
                residentRepository.findAll(pageable) :
                residentRepository.searchResidents(query, age, pageable);

        residentsPage.forEach(resident -> resident.setAge(calculateAge(resident.getBirthDate())));
        return residentsPage;
    }

    @Override
    public void saveResident(Resident resident) {
        if (resident.getId() != null) {
            Optional<Resident> existingResidentOpt = residentRepository.findById(resident.getId());
            existingResidentOpt.ifPresent(existingResident -> {
                existingResident = resident;
                residentRepository.save(existingResident);
            });
        } else {
            residentRepository.save(resident);
        }
    }

    @Override
    public void updateResident(Resident resident) {
        if (resident.getId() == null) throw new IllegalArgumentException("ID must not be null");
        Resident existingResident = residentRepository.findById(resident.getId())
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
        existingResident = resident;
        residentRepository.save(existingResident);
    }

    @Override
    public Resident incrementCertificateCount(Long id, String certificateType) {
        Optional<Resident> residentOpt = residentRepository.findById(id);
        
        if (residentOpt.isEmpty()) {
            throw new RuntimeException("Resident not found for ID: " + id);
        }

        Resident resident = residentOpt.get();
 
        if ("BARANGAY-INDIGENCY".equalsIgnoreCase(certificateType)) {
            resident.setCertificateOfIndigencyCount(resident.getCertificateOfIndigencyCount() + 1);
        } else if ("CLEARANCE".equalsIgnoreCase(certificateType)) {
            resident.setClearanceCount(resident.getClearanceCount() + 1);
        } else if ("FIRST-TIME-JOB-SEEKER".equalsIgnoreCase(certificateType)) {
            resident.setFirstTimeJobSeekerCount(resident.getFirstTimeJobSeekerCount() + 1);
        } else {
            throw new IllegalArgumentException("Invalid certificate type: " + certificateType);
        }

        return residentRepository.save(resident);
    }

    @Override
    public Resident markResidentAsDeceased(Long id, Boolean isDeceased) {
        // Retrieve the resident by ID
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resident not found"));

        // Update the deceased status
        resident.setDeceased(isDeceased);

        // Save the updated resident
        return residentRepository.save(resident);
    }

    @Autowired
    private HouseRepository houseRepository;

    @Override
    public List<House> getHousesForResident(Long residentId) {
        // Find all houses that contain this resident ID
        List<House> allHouses = houseRepository.findAll();
        return allHouses.stream()
            .filter(house -> house.getResidentIds().contains(residentId))
            .collect(Collectors.toList());
    }


    
}
