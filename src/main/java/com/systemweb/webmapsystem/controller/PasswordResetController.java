package com.systemweb.webmapsystem.controller;

import com.systemweb.webmapsystem.service.WebUserServices;
import com.systemweb.webmapsystem.web.PasswordResetDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {

    private final WebUserServices webUserServices;

    @Autowired
    public PasswordResetController(WebUserServices webUserServices) {
        this.webUserServices = webUserServices;
    }

    @PostMapping("/user")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        boolean isUpdated = webUserServices.updatePassword(passwordResetDTO.getEmail(), passwordResetDTO.getNewPassword());
        if (isUpdated) {
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.status(404).body("User with the given email not found.");
        }
    }
    

}

