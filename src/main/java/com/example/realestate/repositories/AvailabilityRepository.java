package com.example.realestate.repositories;

import com.example.realestate.models.Availability;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityRepository
        extends JpaRepository<Availability, Long> {

    List<Availability> findByAgentId(String agentId);
}