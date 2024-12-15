package com.systemweb.webmapsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systemweb.webmapsystem.model.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    
}

