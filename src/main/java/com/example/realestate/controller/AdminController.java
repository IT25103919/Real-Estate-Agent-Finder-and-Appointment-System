package com.example.realestate.controller;

import com.example.realestate.models.Admin;
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
        return ResponseEntity.ok(adminService.getAllUsers());
    }


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


    @GetMapping("/agents/pending")
    public ResponseEntity<List<User>> getPendingAgents() {
        return ResponseEntity.ok(adminService.getPendingAgents());
    }
//  GET ALL COMPLAINTS
    @GetMapping("/complaints")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(adminService.getAllComplaints());
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

    //  NEW: CREATE ANOTHER ADMIN

    @PostMapping("/admins")
    public ResponseEntity<Map<String, String>> createAdmin(@RequestBody Admin newAdmin) {
        Map<String, String> response = new HashMap<>();
        String result = adminService.createAdmin(newAdmin);
        if (result.equals("EMAIL_EXISTS")) {
            response.put("status", "error");
            response.put("message", "Email already registered.");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("status", "success");
        response.put("message", "New admin account created successfully.");
        return ResponseEntity.ok(response);
    }
}