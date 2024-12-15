package com.systemweb.webmapsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.systemweb.webmapsystem.model.WebUser;
@Repository
public interface WebUserRepository extends JpaRepository<WebUser, Long> {
    Optional<WebUser> findByEmail(String email);

    

    @Query("SELECT u FROM WebUser u WHERE LOWER(u.email) = LOWER(:email)")
    WebUser findByEmailIgnoreCase(@Param("email") String email);

}
