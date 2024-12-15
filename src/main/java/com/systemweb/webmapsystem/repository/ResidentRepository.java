package com.systemweb.webmapsystem.repository;

import com.systemweb.webmapsystem.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
    
    @Query("SELECT r FROM Resident r WHERE "
    + "(LOWER(CONCAT(r.firstName, ' ', r.lastName)) LIKE LOWER(CONCAT('%', :query, '%')) "
    + "OR LOWER(r.firstName) LIKE LOWER(CONCAT('%', :query, '%')) "
    + "OR LOWER(r.lastName) LIKE LOWER(CONCAT('%', :query, '%'))) "
    + "AND (:age IS NULL OR r.age = :age) "
    + "AND r.isDeceased = false")  // Add this condition to exclude deceased residents
    Page<Resident> searchResidents(@Param("query") String query, @Param("age") Integer age, Pageable pageable);


    List<Resident> findByGender(String gender);

    List<Resident> findByVotingEligibility(String votingEligibility);

    List<Resident> findByAgeGreaterThanEqual(int age);

    List<Resident> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    List<Resident> findByPwdTrue();

    List<Resident> findByIsDeceasedTrue();

}
