package com.example.realestate.controller;

import com.example.realestate.models.Client;
import com.example.realestate.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/client/profile")
@CrossOrigin(origins = "*")
public class ClientProfileController {

    @Autowired
    private ClientRepository clientRepository;



    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable Long id) {
        Optional<Client> clientOpt = clientRepository.findById(id);
        if (clientOpt.isEmpty()) {
            Map<String, String> err = new HashMap<>();
            err.put("status", "error");
            err.put("message", "Client not found.");
            return ResponseEntity.status(404).body(err);
        }
        return ResponseEntity.ok(clientOpt.get());
    }



    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateProfile(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        Map<String, String> response = new HashMap<>();
        Optional<Client> clientOpt = clientRepository.findById(id);

        if (clientOpt.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Client not found.");
            return ResponseEntity.status(404).body(response);
        }

        Client client = clientOpt.get();

        // Only update fields that were actually sent (null check)
        if (body.containsKey("fullName"))              client.setFullName(body.get("fullName"));
        if (body.containsKey("phone"))                 client.setPhone(body.get("phone"));
        if (body.containsKey("address"))               client.setAddress(body.get("address"));
        if (body.containsKey("preferredPropertyType")) client.setPreferredPropertyType(body.get("preferredPropertyType"));

        clientRepository.save(client);

        response.put("status", "success");
        response.put("message", "Profile updated successfully.");
        return ResponseEntity.ok(response);
    }



    @PutMapping("/{id}/picture")
    public ResponseEntity<Map<String, String>> updatePicture(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        Map<String, String> response = new HashMap<>();
        Optional<Client> clientOpt = clientRepository.findById(id);

        if (clientOpt.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Client not found.");
            return ResponseEntity.status(404).body(response);
        }

        String picture = body.get("profilePicture");
        if (picture == null || picture.isBlank()) {
            response.put("status", "error");
            response.put("message", "No image data received.");
            return ResponseEntity.badRequest().body(response);
        }

        Client client = clientOpt.get();
        client.setProfilePicture(picture);
        clientRepository.save(client);

        response.put("status", "success");
        response.put("message", "Profile picture updated.");
        return ResponseEntity.ok(response);
    }
}