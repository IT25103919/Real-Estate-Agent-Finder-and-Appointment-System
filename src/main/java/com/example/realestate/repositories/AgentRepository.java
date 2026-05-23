package com.example.realestate.repositories;

import com.example.realestate.models.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    // Filter agents by location (case-insensitive partial match)
    // e.g. "colombo" will match "Colombo", "Colombo 03" etc.
    List<Agent> findByLocationContainingIgnoreCase(String location);
}