package com.systemweb.webmapsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import com.systemweb.webmapsystem.model.Establishment;
import com.systemweb.webmapsystem.repository.EstablishmentRepository;
import com.systemweb.webmapsystem.web.EstablishmentDTO;

import java.util.List;

@Service
public class EstablishmentService {

    @Autowired
    private EstablishmentRepository establishmentRepository;
 
    public ResponseEntity<Establishment> addEstablishment(EstablishmentDTO dto) {
        
        Establishment establishment = new Establishment();
        establishment.setName(dto.getName());
        establishment.setDescription(dto.getDescription());
        establishment.setLat(dto.getLat());
        establishment.setLng(dto.getLng());
 
        establishmentRepository.save(establishment);
 
        return ResponseEntity.ok(establishment);
    }
 
    public List<Establishment> getAllEstablishments() { 
        return establishmentRepository.findAll();
    }

    public boolean deleteEstablishment(Long id) {
        if (establishmentRepository.existsById(id)) {
            establishmentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    
}
