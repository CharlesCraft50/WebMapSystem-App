package com.systemweb.webmapsystem.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.mail.javamail.JavaMailSender;

import com.systemweb.webmapsystem.UserAlreadyExistsException;
import com.systemweb.webmapsystem.model.WebRole;
import com.systemweb.webmapsystem.model.WebUser;
import com.systemweb.webmapsystem.repository.WebUserRepository;
import com.systemweb.webmapsystem.web.WebUserDto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class WebUserServicesImpl implements WebUserServices {

    private final WebUserRepository webUserRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender; 

    public WebUserServicesImpl(WebUserRepository webUserRepository, PasswordEncoder passwordEncoder) {
        this.webUserRepository = webUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public WebUser save(WebUserDto webUserDto) {
        String email = webUserDto.getEmail();
    
        // Validate if email already exists
        if (webUserRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("This email is already registered.");
        }
    
        // Validate Gmail address
        if (!isValidGmail(email)) {
            throw new IllegalArgumentException("Invalid Gmail address.");
        }
    
        String rawPassword = webUserDto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
    
        WebUser webUser = new WebUser(
            webUserDto.getFirstName(),
            webUserDto.getLastName(),
            email,
            encodedPassword,
            Collections.singleton(new WebRole("ROLE_USER"))
        );
    
        String otp = generateOtp();
        webUser.setOtp(otp);
        webUser.setOtpExpiry(calculateOtpExpiry());
    
        webUserRepository.save(webUser);
        sendOtpEmail(email, otp);
    
        return webUser;
    }
    
    
    

    private static final String GMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
    private static final Pattern GMAIL_PATTERN = Pattern.compile(GMAIL_REGEX);

    private boolean isValidGmail(String email) {
        if (email == null) return false;
    
        // Use regex only to match valid Gmail addresses
        return GMAIL_PATTERN.matcher(email).matches();
    }
    
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Received email for authentication: '" + username + "'"); 
        WebUser user = webUserRepository.findByEmail(username)
                .orElseThrow(() -> {
                    System.out.println("No user found with username: '" + username + "'");  
                    return new UsernameNotFoundException("User not found with username: " + username);
                });
    
        System.out.println("User found: " + user.getEmail());  
    
        return User.withUsername(user.getEmail())
                   .password(user.getPassword())
                   .authorities(mapRolesToAuthorities(user.getRoles()))
                   .build();
    }
    
    
    
    
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<WebRole> roles) {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public boolean updatePassword(String email, String newPassword) { 
        Optional<WebUser> optionalUser = webUserRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            WebUser user = optionalUser.get();
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            webUserRepository.save(user);
            return true;
        }
        return false;
    }
    
    

    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000)); // 6-digit OTP
    }

    private LocalDateTime calculateOtpExpiry() {
        return LocalDateTime.now().plusMinutes(10); // OTP expires in 10 minutes
    }

    @Override
    public void generateAndSendOtp(String email) {
        WebUser user = webUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    
        String otp = generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(calculateOtpExpiry());
        webUserRepository.save(user);
    
        // Send OTP email
        sendOtpEmail(email, otp);
    }
    

    private void sendOtpEmail(String to, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Your OTP Code");
            helper.setText("Your OTP is: " + otp + ". It expires in 10 minutes.");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        WebUser user = webUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        if (user.getOtp() == null || !user.getOtp().equals(otp) || user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            return false; // OTP is invalid or expired
        }

        user.setOtp(null); // Clear OTP after successful verification
        user.setOtpExpiry(null);
        webUserRepository.save(user);
        return true;
    }
    

    @Override
    public void registerUserAccount(WebUser newUser) {
        Optional<WebUser> existingUser = webUserRepository.findByEmail(newUser.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("Email already exists.");
        }
    
        // Save the new user to the database
        webUserRepository.save(newUser);
    }

    @Override
    public boolean emailExists(String email) {
    return webUserRepository.findByEmail(email).isPresent();
    }

    @Override
    public void activateUser(String email) {
        WebUser user = webUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        // Ensure OTP and expiry are cleared upon successful activation
        user.setOtp(null);
        user.setOtpExpiry(null);

        // Activate the user (set any additional flags or statuses if required)
        user.setEnabled(true); // Assuming you have an "enabled" field in WebUser

        webUserRepository.save(user);
    }

    

    


}
