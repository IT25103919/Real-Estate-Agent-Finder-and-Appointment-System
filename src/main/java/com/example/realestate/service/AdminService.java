package com.example.realestate.service;



import com.example.realestate.models.Complaint;
import com.example.realestate.models.User;
import com.example.realestate.repositories.ComplaintRepository;
import com.example.realestate.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    // ── GET ALL USERS ──────────────────────────────
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ── DELETE A USER ──────────────────────────────
    public String deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return "NOT_FOUND";
        }
        userRepository.deleteById(id);
        return "DELETED";
    }

    // ── BAN A USER ─────────────────────────────────
    public String banUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return "NOT_FOUND";
        }
        User user = userOpt.get();
        user.setStatus(User.Status.BANNED);
        userRepository.save(user);
        return "BANNED";
    }

    // ── UNBAN A USER ───────────────────────────────
    public String unbanUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return "NOT_FOUND";
        }
        User user = userOpt.get();
        user.setStatus(User.Status.ACTIVE);
        userRepository.save(user);
        return "UNBANNED";
    }

    // ── APPROVE AN AGENT ───────────────────────────
    public String approveAgent(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return "NOT_FOUND";
        }
        User user = userOpt.get();
        // Only approve if the user is an AGENT
        if (user.getRole() != User.Role.AGENT) {
            return "NOT_AGENT";
        }
        user.setStatus(User.Status.ACTIVE);
        userRepository.save(user);
        return "APPROVED";
    }

    // ── GET PENDING AGENTS ─────────────────────────
    public List<User> getPendingAgents() {
        // Get all users, then filter only PENDING AGENTS
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getRole() == User.Role.AGENT
                        && u.getStatus() == User.Status.PENDING)
                .toList();
    }

    // ── GET ALL COMPLAINTS ─────────────────────────
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    // ── DELETE / DISMISS A COMPLAINT ───────────────
    public String deleteComplaint(Long id) {
        if (!complaintRepository.existsById(id)) {
            return "NOT_FOUND";
        }
        complaintRepository.deleteById(id);
        return "DELETED";
    }
}
