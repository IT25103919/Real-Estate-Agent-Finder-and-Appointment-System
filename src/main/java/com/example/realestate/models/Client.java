package com.example.realestate.models;

import jakarta.persistence.Entity;

@Entity
public class Client extends User {
    private String preferredPropertyType;

    public Client() {
    }

    public String getPreferredPropertyType() {
        return preferredPropertyType;
    }

    public void setPreferredPropertyType(String preferredPropertyType) {
        this.preferredPropertyType = preferredPropertyType;
    }
}