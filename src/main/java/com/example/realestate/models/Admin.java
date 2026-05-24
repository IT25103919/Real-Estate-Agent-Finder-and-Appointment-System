package com.example.realestate.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;

@Entity
public class Admin extends User {

    private String username;

    private String phone;

    @Enumerated(EnumType.STRING)
    private AdminLevel adminLevel = AdminLevel.ADMIN;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum AdminLevel {
        SUPER_ADMIN,
        ADMIN
    }


    public Admin() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AdminLevel getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(AdminLevel adminLevel) {
        this.adminLevel = adminLevel;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}