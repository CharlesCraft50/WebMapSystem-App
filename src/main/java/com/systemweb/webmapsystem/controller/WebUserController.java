package com.systemweb.webmapsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.systemweb.webmapsystem.UserAlreadyExistsException;
import com.systemweb.webmapsystem.model.WebUser;
import com.systemweb.webmapsystem.service.WebUserServices;
import com.systemweb.webmapsystem.web.WebUserDto;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/Register")
public class WebUserController {

    private WebUserServices webUserServices;

    @Autowired
    public WebUserController(WebUserServices webUserServices) {
        this.webUserServices = webUserServices;
    }

    @ModelAttribute("user")
    public WebUserDto webUserDto() {
        return new WebUserDto("", "", "", "");  
    }

    @GetMapping
    public String getRegister(@RequestParam(value = "success", required = false) String success,
                            @RequestParam(value = "error", required = false) String error,
                            Model model, HttpServletRequest request) {
        if (request.getSession(false) == null) {
            System.out.println("Session not found!");
        } else {
            System.out.println("Session is active!");
        }
        if (success != null) {
            model.addAttribute("success", "Registration successful!");
        }
        if (error != null) {
            model.addAttribute("error", "Invalid OTP. Please try again.");
        }
        return "Register";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new WebUserDto("", "", "", ""));  // Add a new WebUserDto object to the model
        return "Register";  // Thymeleaf will look for Register.html
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") WebUserDto webUserDto, Model model) {
        try {
            webUserServices.save(webUserDto);
            model.addAttribute("otpRequired", true);
            model.addAttribute("email", webUserDto.getEmail());
            return "Register";  // Redirect to the same page for OTP verification
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("error", "This email address is already registered. Please log in.");
            return "Register";  // Show the registration form again with the error
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());  // Specific validation error
            return "Register";  // Show the registration form again with the error
        }
    }
    
    
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp, Model model) {
        boolean isVerified = webUserServices.verifyOtp(email, otp);
    
        if (isVerified) {
            try {
                webUserServices.activateUser(email); // Activate user only if OTP is valid
                return "redirect:/Register?success=true"; // Include success query parameter
            } catch (Exception e) {
                model.addAttribute("error", "An error occurred while activating your account. Please try again.");
                return "Register"; // Redirect back to the registration form with error
            }
        } else {
            return "redirect:/Register?error=true"; // Include error query parameter
        }
    }
    
    
    
    @PostMapping("/generate-otp")
    public String generateOtp(@RequestParam String email, Model model) {
        try {
            webUserServices.generateAndSendOtp(email);
            model.addAttribute("otpRequired", true);
            model.addAttribute("email", email);
            return "Register";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to generate OTP. Please try again.");
            return "Register";
        }
    }
    



}

