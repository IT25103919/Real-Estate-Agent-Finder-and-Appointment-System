package com.example.realestate.repositories;

import com.example.realestate.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Get all properties by a specific agent
    List<Property> findByAgentId(Long agentId);

    // Get sold properties by agent (for sold count on dashboard)
    List<Property> findByAgentIdAndStatus(Long agentId, Property.PropertyStatus status);

    // Filter properties by district for clients browsing
    List<Property> findByDistrictContainingIgnoreCase(String district);
}