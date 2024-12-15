package com.systemweb.webmapsystem.model;

import java.util.stream.Collectors;
import java.util.ArrayList;
import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "house")
public class House {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String houseNumber;
    private String address;
    private String name;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    
    public House(String name, Double lat, Double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "house_residents", 
        joinColumns = @JoinColumn(name = "house_id"), 
        inverseJoinColumns = @JoinColumn(name = "resident_id"))
    private List<Resident> residents = new ArrayList<>();

    public House() {
        
    }    
    
    public List<Long> getResidentIds() {
        return residents.stream()
                        .map(Resident::getId)
                        .collect(Collectors.toList());
    }
    
    

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
