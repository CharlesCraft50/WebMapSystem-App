package com.systemweb.webmapsystem.service;

import com.systemweb.webmapsystem.model.House;
import com.systemweb.webmapsystem.model.Resident;
import com.systemweb.webmapsystem.repository.HouseRepository;
import com.systemweb.webmapsystem.repository.ResidentRepository;
import com.systemweb.webmapsystem.web.HouseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HouseService {

    private final HouseRepository houseRepository;
    private final ResidentRepository residentRepository;

    @Autowired
    public HouseService(HouseRepository houseRepository, ResidentRepository residentRepository) {
        this.houseRepository = houseRepository;
        this.residentRepository = residentRepository;
    }

    public House createHouse(House house) {
        return houseRepository.save(house);
    }

    public House findHouseById(Long id) {
        return houseRepository.findById(id).orElse(null);  
    }

    public House addResidentsToHouse(Long houseId, List<Long> residentIds) {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if (houseOptional.isPresent()) {
            House house = houseOptional.get();
            for (Long residentId : residentIds) {
                Optional<Resident> resident = residentRepository.findById(residentId);
                resident.ifPresent(house.getResidents()::add);
            }
            return houseRepository.save(house);
        }
        return null;
    }
 
    @Transactional
    public List<House> getAllHouses() {
        List<House> houses = houseRepository.findAll();
        
        houses.forEach(house -> house.getResidents().size());
        return houses;
    }

    public HouseDTO convertToDTO(House house) {
    List<Long> residentIds = house.getResidents().stream()
                                  .map(Resident::getId)
                                  .collect(Collectors.toList());
    return new HouseDTO(house.getId(), house.getAddress(), house.getHouseNumber(), house.getName(), house.getLat(), house.getLng(), residentIds);
}
}

