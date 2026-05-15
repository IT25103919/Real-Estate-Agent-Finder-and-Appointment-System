package com.example.realestate.controller;


import com.example.realestate.models.Complaint;
import com.example.realestate.models.User;
import com.example.realestate.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // DELETE A USER

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        String result = adminService.deleteUser(id);

        if (result.equals("NOT_FOUND")) {
            response.put("status", "error");
            response.put("message", "User not found.");
            return ResponseEntity.status(404).body(response);
        }

        response.put("status", "success");
        response.put("message", "User deleted.");
        return ResponseEntity.ok(response);
    }

    // BAN A USER

    @PutMapping("/users/{id}/ban")
    public ResponseEntity<Map<String, String>> banUser(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        String result = adminService.banUser(id);

        if (result.equals("NOT_FOUND")) {
            response.put("status", "error");
            response.put("message", "User not found.");
            return ResponseEntity.status(404).body(response);
        }

        response.put("status", "success");
        response.put("message", "User banned.");
        return ResponseEntity.ok(response);
    }

    //  UNBAN A USER

    @PutMapping("/users/{id}/unban")
    public ResponseEntity<Map<String, String>> unbanUser(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        String result = adminService.unbanUser(id);

        if (result.equals("NOT_FOUND")) {
            response.put("status", "error");
            response.put("message", "User not found.");
            return ResponseEntity.status(404).body(response);
        }

        response.put("status", "success");
        response.put("message", "User unbanned.");
        return ResponseEntity.ok(response);
    }

    // APPROVE AN AGENT

    @PutMapping("/users/{id}/approve")
    public ResponseEntity<Map<String, String>> approveAgent(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        String result = adminService.approveAgent(id);

        if (result.equals("NOT_FOUND")) {
            response.put("status", "error");
            response.put("message", "User not found.");
            return ResponseEntity.status(404).body(response);
        }
        if (result.equals("NOT_AGENT")) {
            response.put("status", "error");
            response.put("message", "This user is not an agent.");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("status", "success");
        response.put("message", "Agent approved.");
        return ResponseEntity.ok(response);
    }

    //  GET PENDING AGENTS
    @GetMapping("/agents/pending")
    public ResponseEntity<List<User>> getPendingAgents() {
        List<User> pending = adminService.getPendingAgents();
        return ResponseEntity.ok(pending);
    }

    //  GET ALL COMPLAINTS

    @GetMapping("/complaints")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = adminService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }

    //  DISMISS A COMPLAINT

    @DeleteMapping("/complaints/{id}")
    public ResponseEntity<Map<String, String>> deleteComplaint(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        String result = adminService.deleteComplaint(id);

        if (result.equals("NOT_FOUND")) {
            response.put("status", "error");
            response.put("message", "Complaint not found.");
            return ResponseEntity.status(404).body(response);
        }

        response.put("status", "success");
        response.put("message", "Complaint dismissed.");
        return ResponseEntity.ok(response);
    }
}
