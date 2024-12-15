package com.systemweb.webmapsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systemweb.webmapsystem.model.Establishment;
import com.systemweb.webmapsystem.service.EstablishmentService;
import com.systemweb.webmapsystem.web.EstablishmentDTO;

@RestController
@RequestMapping("/api/establishment")
public class EstablishmentController {

    @Autowired
    private EstablishmentService establishmentService;

    @PostMapping("/add")
    public ResponseEntity<Establishment> addEstablishment(@RequestBody EstablishmentDTO dto) {
      
        return establishmentService.addEstablishment(dto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Establishment>> getAllEstablishments() {
        List<Establishment> establishments = establishmentService.getAllEstablishments();
        return ResponseEntity.ok(establishments);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEstablishment(@PathVariable Long id) {
        boolean deleted = establishmentService.deleteEstablishment(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
