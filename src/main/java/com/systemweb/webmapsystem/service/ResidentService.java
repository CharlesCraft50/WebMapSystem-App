package com.systemweb.webmapsystem.service;

import com.systemweb.webmapsystem.model.House;
import com.systemweb.webmapsystem.model.Resident;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ResidentService {

    List<Resident> getAllResidents();

    void saveResident(Resident resident);

    Optional<Resident> findById(Long id);

    Page<Resident> findResidents(String query, Integer age, int page, int size);

    void updateResident(Resident resident);

    Resident incrementCertificateCount(Long residentId, String certificateType);

    public Resident markResidentAsDeceased(Long id, Boolean isDeceased);

    List<House> getHousesForResident(Long residentId);

    
}
