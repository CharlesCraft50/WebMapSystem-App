package com.systemweb.webmapsystem.web;

import java.util.List;

public class HouseWithResidentsDTO {
    private HouseDTO house;
    private List<ResidentDTO> residents;
 
    public HouseWithResidentsDTO(HouseDTO house, List<ResidentDTO> residents) {
        this.house = house;
        this.residents = residents;
    }

    // Getters and Setters
    public HouseDTO getHouse() {
        return house;
    }

    public void setHouse(HouseDTO house) {
        this.house = house;
    }

    public List<ResidentDTO> getResidents() {
        return residents;
    }

    public void setResidents(List<ResidentDTO> residents) {
        this.residents = residents;
    }
}
