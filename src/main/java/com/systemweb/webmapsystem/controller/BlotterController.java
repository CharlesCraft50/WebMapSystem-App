package com.systemweb.webmapsystem.controller;

import com.systemweb.webmapsystem.model.Record;
import com.systemweb.webmapsystem.repository.RecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class BlotterController {

    @Autowired
    private RecordRepository recordRepository;

    @GetMapping("/Blotter")
    public String showBlotterPage(Model model) {
        List<Record> records = recordRepository.findAll();
        model.addAttribute("records", records);
        return "Blotter";
    }

    @PostMapping("/submit")
    public String submitForm(
        @RequestParam String complainant,
        @RequestParam String respondent,
        @RequestParam String description,
        @RequestParam String location,
        @RequestParam String date,
        @RequestParam String recordedBy,
        @RequestParam String issued,
        @RequestParam(defaultValue = "pending") String status,
        @RequestParam(required = false) String caseNumber // Optional
    ) {
        Record record = new Record();

        // Generate case number if not provided
        if (caseNumber == null || caseNumber.isEmpty()) {
            caseNumber = "CN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }

        record.setCaseNumber(caseNumber);
        record.setComplainant(complainant);
        record.setRespondent(respondent);
        record.setDescription(description);
        record.setLocation(location);
        record.setDate(date);
        record.setRecordedBy(recordedBy);
        record.setStatus(status);
        record.setIssued(issued);

        recordRepository.save(record);

        return "redirect:/Blotter";
    }
}