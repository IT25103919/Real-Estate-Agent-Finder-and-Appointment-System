package com.example.realestate.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Agent extends User {

    private String licenseNumber;

    // NEW fields for agent portfolio
    private String location;       // e.g. "Colombo", "Kandy"

    private String bio;            // short description about the agent

    @Column(columnDefinition = "LONGTEXT")
    private String photo;

    // Number of properties sold (shown on portfolio)
    private Integer soldProperties = 0;

    public Agent() {}

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }

    public Integer getSoldProperties() { return soldProperties; }
    public void setSoldProperties(Integer soldProperties) { this.soldProperties = soldProperties; }
}