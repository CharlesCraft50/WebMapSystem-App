package com.systemweb.webmapsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.systemweb.webmapsystem.service.RecordService;
import com.systemweb.webmapsystem.web.StatusUpdateRequest;

@RestController
@RequestMapping("/api/blotter")  // Add base path
public class BlotterAPIController {
    
    @Autowired
    private RecordService recordService;

    @DeleteMapping("/record/{id}")
    public ResponseEntity<?> deleteRecord(@PathVariable Long id) {
        boolean isDeleted = recordService.deleteRecord(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not found");
        }
    }

    @PostMapping("/record/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id, 
            @RequestBody StatusUpdateRequest request) {
        boolean success = recordService.updateStatus(id, request.getStatus());
        return success ? 
            ResponseEntity.ok().build() : 
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
