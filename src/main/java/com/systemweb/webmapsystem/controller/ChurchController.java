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

import com.systemweb.webmapsystem.model.Church;
import com.systemweb.webmapsystem.model.School;
import com.systemweb.webmapsystem.service.ChurchService;

@RestController
@RequestMapping("/api/church")
public class ChurchController {
    @Autowired
    private ChurchService churchService;

    @PostMapping("/add")
    public ResponseEntity<Church> addChurch(@RequestBody Church church) {
        return ResponseEntity.ok(churchService.save(church));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Church>> getAllChurches() {
        return ResponseEntity.ok(churchService.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteChurch(@PathVariable Long id) {
        churchService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

