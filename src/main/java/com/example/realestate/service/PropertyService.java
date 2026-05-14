package com.example.realestate.service;

import com.example.realestate.models.Property;
import com.example.realestate.models.ResidentialProperty;
import com.example.realestate.models.CommercialProperty;
import com.example.realestate.repositories.PropertyRepository;
import com.example.realestate.repositories.ResidentialPropertyRepository;
import com.example.realestate.repositories.CommercialPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ResidentialPropertyRepository residentialPropertyRepository;

    @Autowired
    private CommercialPropertyRepository commercialPropertyRepository;

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public ResidentialProperty saveResidential(ResidentialProperty property) {
        return residentialPropertyRepository.save(property);
    }

    public CommercialProperty saveCommercial(CommercialProperty property) {
        return commercialPropertyRepository.save(property);
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}