package com.systemweb.webmapsystem.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.systemweb.webmapsystem.model.WebUser;
import com.systemweb.webmapsystem.web.WebUserDto;

@Service
public interface WebUserServices extends UserDetailsService{
    
    WebUser save(WebUserDto webUserDto);
    UserDetails loadUserByUsername(String username);
    boolean updatePassword(String email, String newPassword);
    void generateAndSendOtp(String email);
    boolean verifyOtp(String email, String otp);
    void registerUserAccount(WebUser newUser);
    boolean emailExists(String email);
    void activateUser(String email);
}
