package com.example.realestate.controller;

import com.example.realestate.models.User;
import com.example.realestate.service.UserService;
import com.example.realestate.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")  // allows your HTML frontend to call this API
public class AuthController {

    @Autowired
    private UserService userService;


    @Autowired
    private UserRepository userRepository;

    // REGISTER endpoint
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        String result = userService.register(user);

        if (result.equals("EMAIL_EXISTS")) {
            response.put("status", "error");
            response.put("message", "Email already registered.");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("status", "success");
        response.put("message", "Account created successfully!");
        return ResponseEntity.ok(response);
    }

    // LOGIN endpoint
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        // First check if the user exists at all
        Optional<User> userOpt = userRepository.findByEmail(email);
        Map<String, String> response = new HashMap<>();

        if (userOpt.isEmpty()) {
            response.put("status", "error");
            response.put("message", "No account found with this email.");
            return ResponseEntity.status(401).body(response);
        }

        User foundUser = userOpt.get();

        // Check if banned
        if (foundUser.getStatus() == User.Status.BANNED) {
            response.put("status", "error");
            response.put("message", "Your account has been banned. Contact admin.");
            return ResponseEntity.status(403).body(response);
        }

        // Check if pending (agent not approved yet)
        if (foundUser.getStatus() == User.Status.PENDING) {
            response.put("status", "error");
            response.put("message", "Your agent account is pending admin approval.");
            return ResponseEntity.status(403).body(response);
        }

        // Now check password
        User user = userService.login(email, password);

        if (user == null) {
            response.put("status", "error");
            response.put("message", "Wrong password.");
            return ResponseEntity.status(401).body(response);
        }

        response.put("status", "success");
        response.put("role", user.getRole().toString());
        response.put("name", user.getFullName());
        return ResponseEntity.ok(response);
    }
}