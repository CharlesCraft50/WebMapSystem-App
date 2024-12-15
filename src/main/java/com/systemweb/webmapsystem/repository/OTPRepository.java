package com.systemweb.webmapsystem.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systemweb.webmapsystem.model.OTP;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByEmailAndCode(String email, String code);
    Optional<OTP> findByEmail(String email);
    void deleteAllByExpiryDateBefore(LocalDateTime expiryDate);

}
