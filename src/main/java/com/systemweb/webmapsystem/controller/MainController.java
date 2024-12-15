package com.systemweb.webmapsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String login() {
        return "login"; 
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }
    

    @GetMapping("/Home")
    public String home() {
        return "Home"; 
    }       

    @GetMapping("/NewPassword")
    public String newPassword() {
        return "NewPassword"; 
    }   

    @GetMapping("/Mapping")
    public String getMapping() {
        return "Mapping";
    }

    @GetMapping("/FormMenu")
    public String getFormMenu() {
        return "FormMenu";
    }

    @GetMapping("/ViewRecords")
    public String getViewRecords() {
        return "ViewRecords";
    }
    

}
