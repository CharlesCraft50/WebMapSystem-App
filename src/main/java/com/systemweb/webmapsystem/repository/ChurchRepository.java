package com.systemweb.webmapsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.systemweb.webmapsystem.model.Church;

@Repository
public interface ChurchRepository extends JpaRepository<Church, Long> {}