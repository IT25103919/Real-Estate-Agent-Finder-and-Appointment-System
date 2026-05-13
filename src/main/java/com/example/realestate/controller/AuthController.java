package com.example.realestate.controller;
import com.example.realestate.models.User;
import com.example.realestate.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")  // allows your HTML frontend to call this API
public class AuthController {

    @Autowired
    private UserService userService;

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
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User user = userService.login(email, password);
        Map<String, String> response = new HashMap<>();

        if (user == null) {
            response.put("status", "error");
            response.put("message", "Invalid email or password.");
            return ResponseEntity.status(401).body(response);
        }

        response.put("status", "success");
        response.put("role", user.getRole().toString());
        response.put("name", user.getFullName());
        return ResponseEntity.ok(response);
    }
}
