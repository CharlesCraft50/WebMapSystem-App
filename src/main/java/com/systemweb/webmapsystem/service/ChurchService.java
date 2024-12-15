package com.systemweb.webmapsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systemweb.webmapsystem.model.Church;
import com.systemweb.webmapsystem.repository.ChurchRepository;

@Service
public class ChurchService {
    @Autowired
    private ChurchRepository churchRepository;

    public Church save(Church church) {
        return churchRepository.save(church);
    }

    public List<Church> findAll() {
        return churchRepository.findAll();
    }

    public void deleteById(Long id) {
        churchRepository.deleteById(id);
    }
}

