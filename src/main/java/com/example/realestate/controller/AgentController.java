package com.example.realestate.controller;

import com.example.realestate.models.Agent;
import com.example.realestate.models.Property;
import com.example.realestate.models.User;
import com.example.realestate.repositories.AgentRepository;
import com.example.realestate.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agents")
@CrossOrigin(origins = "*")
public class AgentController {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    // GET /api/agents  or  GET /api/agents?location=colombo
    @GetMapping
    public ResponseEntity<List<Agent>> getAgents(
            @RequestParam(required = false) String location) {

        List<Agent> agents;
        if (location != null && !location.isBlank()) {
            agents = agentRepository.findByLocationContainingIgnoreCase(location);
        } else {
            agents = agentRepository.findAll();
        }
        agents = agents.stream()
                .filter(a -> a.getStatus() == User.Status.ACTIVE)
                .toList();
        return ResponseEntity.ok(agents);
    }

    // GET /api/agents/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getAgentById(@PathVariable Long id) {
        Optional<Agent> opt = agentRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.status(404).body("Agent not found.");
        return ResponseEntity.ok(opt.get());
    }

    // GET /api/agents/{id}/properties  — for client-facing portfolio page
    @GetMapping("/{id}/properties")
    public ResponseEntity<List<Property>> getAgentProperties(@PathVariable Long id) {
        return ResponseEntity.ok(propertyRepository.findByAgentId(id));
    }
}