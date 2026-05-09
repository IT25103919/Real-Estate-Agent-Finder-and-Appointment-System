package com.example.realestate.controller;

import com.example.realestate.models.CommercialProperty;
import com.example.realestate.models.Property;
import com.example.realestate.models.ResidentialProperty;
import com.example.realestate.repositories.CommercialPropertyRepository;
import com.example.realestate.repositories.PropertyRepository;
import com.example.realestate.repositories.ResidentialPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ResidentialPropertyRepository residentialPropertyRepository;

    @Autowired
    private CommercialPropertyRepository commercialPropertyRepository;

    @GetMapping
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @PostMapping("/residential")
    public ResidentialProperty addResidential(@RequestBody ResidentialProperty property) {
        return residentialPropertyRepository.save(property);
    }

    @PostMapping("/commercial")
    public CommercialProperty addCommercial(@RequestBody CommercialProperty property) {
        return commercialPropertyRepository.save(property);
    }
}