package com.systemweb.webmapsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systemweb.webmapsystem.model.School;
import com.systemweb.webmapsystem.repository.SchoolRepository;

@Service
public class SchoolService {
    @Autowired
    private SchoolRepository schoolRepository;

    public School save(School school) {
        return schoolRepository.save(school);
    }

    public List<School> findAll() {
        return schoolRepository.findAll();
    }

    public void deleteById(Long id) {
        schoolRepository.deleteById(id);
    }
}
