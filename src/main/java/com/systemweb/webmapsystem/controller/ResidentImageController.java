package com.systemweb.webmapsystem.controller;

import com.systemweb.webmapsystem.model.Resident;
import com.systemweb.webmapsystem.repository.ResidentRepository;
import com.systemweb.webmapsystem.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class ResidentImageController {

    private ResidentRepository residentRepository;

    @Autowired
    private ResidentService residentService;

    @GetMapping("/resident/photo/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
        Optional<Resident> residentOptional = residentService.findById(id);

        if (residentOptional.isEmpty() || residentOptional.get().getPhoto() == null) {
            // Load the default photo
            try {
                Path path = Paths.get("src/main/resources/static/image/Person-Placeholder.jpg");
                byte[] imageBytes = Files.readAllBytes(path);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "image/png")
                        .body(imageBytes);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(residentOptional.get().getPhoto());
    }

}

