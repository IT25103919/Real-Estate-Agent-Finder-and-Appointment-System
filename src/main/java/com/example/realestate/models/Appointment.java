package com.example.realestate.models;

import jakarta.persistence.*;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String notes;

    public Appointment() {
    }

        this.id = id;
        this.status = status;
        this.notes = notes;
    }
