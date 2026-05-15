package com.example.realestate.controller;

import com.example.realestate.models.CommercialProperty;
import com.example.realestate.models.Property;
import com.example.realestate.models.ResidentialProperty;
import com.example.realestate.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @PostMapping("/residential")
    public ResidentialProperty addResidential(@RequestBody ResidentialProperty property) {
        return propertyService.saveResidential(property);
    }

    @PostMapping("/commercial")
    public CommercialProperty addCommercial(@RequestBody CommercialProperty property) {
        return propertyService.saveCommercial(property);
    }

    @DeleteMapping("/{id}")
    public String deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return "Property with ID " + id + " deleted successfully!";
    }

    @PutMapping("/residential/{id}")
    public ResponseEntity<Property> updateResidential(@PathVariable Long id, @RequestBody ResidentialProperty details) {
        Property updated = propertyService.updateResidentialProperty(id, details);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/commercial/{id}")
    public ResponseEntity<Property> updateCommercial(@PathVariable Long id, @RequestBody CommercialProperty details) {
        Property updated = propertyService.updateCommercialProperty(id, details);
        return ResponseEntity.ok(updated);
    }
    @PatchMapping("/{id}/favourite")
    public ResponseEntity<Property> toggleFavourite(@PathVariable Long id) {
        Property updatedProperty = propertyService.toggleFavourite(id);
        return ResponseEntity.ok(updatedProperty);
    }



}