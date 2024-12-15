package com.systemweb.webmapsystem.model;


import org.springframework.cglib.core.Local;

import jakarta.persistence.*;

@Entity
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String owner;
    private String address;
    private String startDate;
    private String lat;
    private String lng;

    public Business() {}

    public Business(String name, String owner, String address, String startDate, String lat, String lng) {  
        this.name = name;
        this.owner = owner;
        this.address = address;
        this.startDate = startDate;
        this.lat = lat; 
        this.lng = lng;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }       

    public String getLng() {
        return lng;
    }   

    public void setLng(String lng) {
        this.lng = lng;
    }

}

