package com.example.realestate.repositories;

import com.example.realestate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    // NEW: find users by role and status
    List<User> findByRoleAndStatus(User.Role role, User.Status status);
}