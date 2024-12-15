package com.systemweb.webmapsystem.repository;

import com.systemweb.webmapsystem.model.House;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HouseRepository extends JpaRepository<House, Long> {
    @Query("SELECT h FROM House h JOIN h.residents r WHERE r.id = :residentId")
    Optional<House> findHouseByResidentId(@Param("residentId") Long residentId);

}
