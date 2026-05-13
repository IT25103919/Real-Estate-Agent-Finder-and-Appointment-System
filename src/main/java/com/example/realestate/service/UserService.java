package com.example.realestate.service;

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
    public String register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return "EMAIL_EXISTS";
        }
        // Save password as plain text (no hashing)
        userRepository.save(user);
        return "SUCCESS";
    }

    // LOGIN - directly compare plain text passwords
    public User login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Simple string comparison
            if (user.getPassword().equals(password)) {
                return user;  // login success
            }
        }
        return null;  // login failed
    }
}
