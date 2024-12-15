package com.systemweb.webmapsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemweb.webmapsystem.model.Business;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    @Query("SELECT b FROM Business b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Business> searchByName(@Param("query") String query);
    List<Business> findAll();
}

