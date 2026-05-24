package com.example.realestate.models;

import jakarta.persistence.Entity;

@Entity
public class Agent extends User {
    private String licenseNumber;

    public Agent() {}

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}