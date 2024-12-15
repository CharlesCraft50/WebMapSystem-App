package com.systemweb.webmapsystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systemweb.webmapsystem.model.Record;
import com.systemweb.webmapsystem.repository.RecordRepository;


@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    // Method to delete a record by ID
    public boolean deleteRecord(Long id) {
        try {
            // Check if the record exists
            if (recordRepository.existsById(id)) {
                // Delete the record
                recordRepository.deleteById(id);
                return true;
            } else {
                return false; // Record doesn't exist
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the error (you can improve this for production)
            return false; // In case of any error, return false
        }
    }

    public boolean updateStatus(Long id, String status) {
        Optional<Record> recordOptional = recordRepository.findById(id);
        if (recordOptional.isPresent()) {
            Record record = recordOptional.get();
            record.setStatus(status);
            recordRepository.save(record);
            return true;
        }
        return false;
    }
}

