package com.systemweb.webmapsystem.web;

import java.util.List;

public class HouseDTO {

    private Long id;
    private String houseNumber;
    private String address;
    private String name;
    private Double lat;
    private Double lng;
    private List<Long> residentIds;  
    
    public HouseDTO() {}

 
    public HouseDTO(Long id, String houseNumber, String address, String name, Double lat, Double lng, List<Long> residentIds) {
        this.id = id;
        this.houseNumber = houseNumber;
        this.address = address;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.residentIds = residentIds;
    }

 
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getHouseNumber() { return houseNumber; }
    public void setHouseNumber(String houseNumber) { this.houseNumber = houseNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getLat() { return lat; }
    public void setLat(Double lat) { this.lat = lat; }

    public Double getLng() { return lng; }
    public void setLng(Double lng) { this.lng = lng; }

    public List<Long> getResidentIds() { return residentIds; }
    public void setResidentIds(List<Long> residentIds) { this.residentIds = residentIds; }
}
