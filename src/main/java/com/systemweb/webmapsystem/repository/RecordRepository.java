package com.systemweb.webmapsystem.repository;

import com.systemweb.webmapsystem.model.Record; 
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
