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

import com.systemweb.webmapsystem.model.School;
import com.systemweb.webmapsystem.service.SchoolService;

@RestController
@RequestMapping("/api/school")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @PostMapping("/add")
    public ResponseEntity<School> addSchool(@RequestBody School school) {
        return ResponseEntity.ok(schoolService.save(school));
    }

    @GetMapping("/all")
    public ResponseEntity<List<School>> getAllSchools() {
        return ResponseEntity.ok(schoolService.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
        schoolService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

