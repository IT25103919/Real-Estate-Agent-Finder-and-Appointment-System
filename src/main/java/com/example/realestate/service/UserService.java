package com.example.realestate.service;

import com.example.realestate.models.Agent;
import com.example.realestate.models.Client;
import com.example.realestate.models.User;
import com.example.realestate.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // REGISTER a new user
    // ROOT CAUSE FIX: Previously this saved every user as a plain User object.
    // With JOINED inheritance, a Client must be saved as a Client instance
    // (so a row is created in both the `user` table AND the `client` table).
    // Same for Agent → `agent` table. Without this, complaint lookups fail
    // because Hibernate JOINs to the `client` / `agent` sub-tables and finds nothing.
    public String register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return "EMAIL_EXISTS";
        }

        User toSave;

        if (user.getRole() == User.Role.AGENT) {
            // Create an Agent instance so the `agent` sub-table gets a row
            Agent agent = new Agent();
            agent.setFullName(user.getFullName());
            agent.setEmail(user.getEmail());
            agent.setPassword(user.getPassword());
            agent.setRole(User.Role.AGENT);
            agent.setStatus(User.Status.PENDING); // needs admin approval
            toSave = agent;

        } else if (user.getRole() == User.Role.CLIENT) {
            // Create a Client instance so the `client` sub-table gets a row
            Client client = new Client();
            client.setFullName(user.getFullName());
            client.setEmail(user.getEmail());
            client.setPassword(user.getPassword());
            client.setRole(User.Role.CLIENT);
            client.setStatus(User.Status.ACTIVE);
            toSave = client;

        } else {
            // ADMIN or anything else — save as plain User
            user.setStatus(User.Status.ACTIVE);
            toSave = user;
        }

        userRepository.save(toSave);
        return "SUCCESS";
    }

    // LOGIN - plain text password comparison (unchanged)
    public User login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}