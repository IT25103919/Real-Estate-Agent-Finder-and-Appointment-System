package com.example.realestate.models;

import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class ResidentialProperty extends Property {
    private int bedrooms;
    private int bathrooms;

    public ResidentialProperty() {
        super();
    }

    public ResidentialProperty(String title, String description, String location, double price, String imagePath, LocalDate addedDate, String status, int bedrooms, int bathrooms) {
        super(title, description, location, price, imagePath, addedDate, status);
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
    }

    public int getBedrooms() {
        return bedrooms;
    }
    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }
    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }
}