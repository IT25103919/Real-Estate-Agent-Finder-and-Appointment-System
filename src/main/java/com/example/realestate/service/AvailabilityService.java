package com.example.realestate.service;

import com.example.realestate.models.Availability;
import com.example.realestate.repositories.AvailabilityRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityService {

    private final AvailabilityRepository repository;

    public AvailabilityService(
            AvailabilityRepository repository
    ) {
        this.repository = repository;
    }

    public Availability addAvailability(
            Availability availability
    ) {
        return repository.save(availability);
    }

    public List<Availability> getByAgent(
            String agentId
    ) {
        return repository.findByAgentId(agentId);
    }

    public void deleteAvailability(Long id) {
        repository.deleteById(id);
    }

}
