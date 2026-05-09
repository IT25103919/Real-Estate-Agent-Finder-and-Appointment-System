package com.example.realestate.models;

import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class CommercialProperty extends Property {
    private double squareFeet;

    public CommercialProperty() {
        super();
    }

    public CommercialProperty(String title, String description, String location, double price, String imagePath, LocalDate addedDate, String status, double squareFeet) {
        super(title, description, location, price, imagePath, addedDate, status);
        this.squareFeet = squareFeet;
    }

    public double getSquareFeet() {
        return squareFeet;
    }
    public void setSquareFeet(double squareFeet) {
        this.squareFeet = squareFeet;
    }
}