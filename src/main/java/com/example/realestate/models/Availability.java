package com.example.realestate.models;

import jakarta.persistence.*;

@Entity
@Table(name = "availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String agentId;

    private String date;

    private String time;

    public Availability() {
    }

    public Availability(
            String agentId,
            String date,
            String time
    ) {
        this.agentId = agentId;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}