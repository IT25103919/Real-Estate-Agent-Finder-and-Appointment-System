package com.example.realestate.controller;

import com.example.realestate.models.Property;
import com.example.realestate.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*")
public class Propertylistingcontroller {

    @Autowired
    private PropertyRepository propertyRepository;

    // GET /api/properties  — all properties (public, for clients)
    // Supports optional query params: type, district, minPrice, maxPrice
    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        List<Property> properties = propertyRepository.findAll();

        if (type != null && !type.isBlank()) {
            try {
                Property.PropertyType pt = Property.PropertyType.valueOf(type.toUpperCase());
                properties = properties.stream()
                        .filter(p -> p.getPropertyType() == pt)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException ignored) {}
        }

        if (district != null && !district.isBlank()) {
            String d = district.toLowerCase();
            properties = properties.stream()
                    .filter(p -> p.getDistrict() != null && p.getDistrict().toLowerCase().contains(d))
                    .collect(Collectors.toList());
        }

        if (minPrice != null) {
            properties = properties.stream()
                    .filter(p -> p.getPrice() != null && p.getPrice() >= minPrice)
                    .collect(Collectors.toList());
        }

        if (maxPrice != null) {
            properties = properties.stream()
                    .filter(p -> p.getPrice() != null && p.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(properties);
    }

    // GET /api/properties/{id}  — single property detail
    @GetMapping("/{id}")
    public ResponseEntity<?> getProperty(@PathVariable Long id) {
        return propertyRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body("Property not found."));
    }

    // DELETE /api/properties/{id}  — admin can delete any property
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProperty(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        if (!propertyRepository.existsById(id)) {
            response.put("status", "error");
            response.put("message", "Property not found.");
            return ResponseEntity.status(404).body(response);
        }
        propertyRepository.deleteById(id);
        response.put("status", "success");
        response.put("message", "Property deleted successfully.");
        return ResponseEntity.ok(response);
    }
}