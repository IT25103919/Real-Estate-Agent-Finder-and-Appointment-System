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

    public Property updateResidentialProperty(Long id, ResidentialProperty updatedDetails) {
        if (residentialPropertyRepository.existsById(id)) {
            updatedDetails.setId(id);
            return residentialPropertyRepository.save(updatedDetails);
        } else {
            throw new RuntimeException("Residential Property not found with id " + id);
        }
    }

    public Property updateCommercialProperty(Long id, CommercialProperty updatedDetails) {
        if (commercialPropertyRepository.existsById(id)) {
            updatedDetails.setId(id);
            return commercialPropertyRepository.save(updatedDetails);
        } else {
            throw new RuntimeException("Commercial Property not found with id " + id);
        }
    }
    public Property toggleFavourite(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + id));

        property.setIsFavourite(!property.isIsFavourite());

        return propertyRepository.save(property);
    }



}