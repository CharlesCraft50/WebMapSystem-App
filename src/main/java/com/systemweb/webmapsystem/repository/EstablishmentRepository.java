package com.systemweb.webmapsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.systemweb.webmapsystem.model.Establishment;

public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {
    
}
