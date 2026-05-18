package com.example.realestate.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Client extends User {

    private String preferredPropertyType;

    private String phone;

    private String address;


    @Column(columnDefinition = "TEXT")
    private String profilePicture;

    public Client() {}

    public String getPreferredPropertyType() { return preferredPropertyType; }
    public void setPreferredPropertyType(String preferredPropertyType) { this.preferredPropertyType = preferredPropertyType; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }
}