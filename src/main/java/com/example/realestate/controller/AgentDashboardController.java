package com.example.realestate.controller;

import com.example.realestate.models.Agent;
import com.example.realestate.models.Property;
import com.example.realestate.repositories.AgentRepository;
import com.example.realestate.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/agent")
@CrossOrigin(origins = "*")
public class AgentDashboardController {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private PropertyRepository propertyRepository;


    // GET /api/agent/{id}/profile
    @GetMapping("/{id}/profile")
    public ResponseEntity<?> getProfile(@PathVariable Long id) {
        Optional<Agent> opt = agentRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.status(404).body("Agent not found.");
        return ResponseEntity.ok(opt.get());
    }

    // ── UPDATE AGENT PROFILE

    // Body: { "fullName","bio","location","licenseNumber","photo" }
    @PutMapping("/{id}/profile")
    public ResponseEntity<Map<String, String>> updateProfile(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        Map<String, String> response = new HashMap<>();
        Optional<Agent> opt = agentRepository.findById(id);
        if (opt.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Agent not found.");
            return ResponseEntity.status(404).body(response);
        }

        Agent agent = opt.get();
        if (body.containsKey("fullName"))      agent.setFullName(body.get("fullName"));
        if (body.containsKey("bio"))           agent.setBio(body.get("bio"));
        if (body.containsKey("location"))      agent.setLocation(body.get("location"));
        if (body.containsKey("licenseNumber")) agent.setLicenseNumber(body.get("licenseNumber"));
        if (body.containsKey("photo"))         agent.setPhoto(body.get("photo"));

        agentRepository.save(agent);
        response.put("status", "success");
        response.put("message", "Profile updated.");
        return ResponseEntity.ok(response);
    }

    // ── GET ALL PROPERTIES BY AGENT ───────────────
    // GET /api/agent/{id}/properties
    @GetMapping("/{id}/properties")
    public ResponseEntity<List<Property>> getProperties(@PathVariable Long id) {
        return ResponseEntity.ok(propertyRepository.findByAgentId(id));
    }

    // ── ADD A PROPERTY ────────────────────────────
    // POST /api/agent/{id}/properties
    @PostMapping("/{id}/properties")
    public ResponseEntity<Map<String, String>> addProperty(
            @PathVariable Long id,
            @RequestBody Property property) {

        Map<String, String> response = new HashMap<>();
        Optional<Agent> opt = agentRepository.findById(id);
        if (opt.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Agent not found.");
            return ResponseEntity.status(404).body(response);
        }

        property.setAgent(opt.get());
        property.setStatus(Property.PropertyStatus.AVAILABLE);
        propertyRepository.save(property);

        response.put("status", "success");
        response.put("message", "Property listed successfully.");
        return ResponseEntity.ok(response);
    }

    // ── MARK PROPERTY AS SOLD ─────────────────────
    // PUT /api/agent/properties/{propertyId}/sold
    // When marked sold, agent's soldProperties count increments automatically
    @PutMapping("/properties/{propertyId}/sold")
    public ResponseEntity<Map<String, String>> markSold(@PathVariable Long propertyId) {
        Map<String, String> response = new HashMap<>();
        Optional<Property> opt = propertyRepository.findById(propertyId);
        if (opt.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Property not found.");
            return ResponseEntity.status(404).body(response);
        }

        Property property = opt.get();
        property.setStatus(Property.PropertyStatus.SOLD);
        propertyRepository.save(property);

        // Auto-increment agent's sold count
        Agent agent = property.getAgent();
        agent.setSoldProperties(
                (agent.getSoldProperties() == null ? 0 : agent.getSoldProperties()) + 1
        );
        agentRepository.save(agent);

        response.put("status", "success");
        response.put("message", "Property marked as sold. Agent stats updated.");
        return ResponseEntity.ok(response);
    }

    // ── DELETE A PROPERTY ─────────────────────────
    // DELETE /api/agent/properties/{propertyId}
    @DeleteMapping("/properties/{propertyId}")
    public ResponseEntity<Map<String, String>> deleteProperty(@PathVariable Long propertyId) {
        Map<String, String> response = new HashMap<>();
        if (!propertyRepository.existsById(propertyId)) {
            response.put("status", "error");
            response.put("message", "Property not found.");
            return ResponseEntity.status(404).body(response);
        }
        propertyRepository.deleteById(propertyId);
        response.put("status", "success");
        response.put("message", "Property deleted.");
        return ResponseEntity.ok(response);
    }
}