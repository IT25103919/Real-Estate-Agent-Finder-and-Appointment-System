package com.example.realestate.controller;

import com.example.realestate.dto.ComplaintRequest;
import com.example.realestate.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/client")
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    private ComplaintService complaintService;


    @PostMapping("/complaints")
    public ResponseEntity<Map<String, String>> fileComplaint(@RequestBody ComplaintRequest request) {
        Map<String, String> response = new HashMap<>();
        String result = complaintService.fileComplaint(request);

        if (result.equals("CLIENT_NOT_FOUND")) {
            response.put("status", "error");
            response.put("message", "Client not found. Make sure you are logged in correctly.");
            return ResponseEntity.status(404).body(response);
        }

        if (result.equals("AGENT_NOT_FOUND")) {
            response.put("status", "error");
            response.put("message", "Agent not found. Please check the Agent ID.");
            return ResponseEntity.status(404).body(response);
        }

        response.put("status", "success");
        response.put("message", "Complaint submitted successfully! The admin will review it.");
        return ResponseEntity.ok(response);
    }
}