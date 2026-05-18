package com.example.realestate.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // වෙනස් කළ කොටස: Client සමඟ Many-to-One සම්බන්ධතාවය
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // වෙනස් කළ කොටස: Agent සමඟ Many-to-One සම්බන්ධතාවය
    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @Column(name = "property_id", nullable = false)
    private Long propertyId;

    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.UPCOMING;

    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Default Constructor
    public Appointment() {
    }

    // All-Args Constructor (අලුත් Object mappings ද ඇතුළත්ව)
    public Appointment(Long id, Client client, Agent agent, Long propertyId, LocalDateTime scheduledAt, AppointmentStatus status, String notes, LocalDateTime createdAt) {
        this.id = id;
        this.client = client;
        this.agent = agent;
        this.propertyId = propertyId;
        this.scheduledAt = scheduledAt;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Agent getAgent() { return agent; }
    public void setAgent(Agent agent) { this.agent = agent; }

    public Long getPropertyId() { return propertyId; }
    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }

    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }

    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public enum AppointmentStatus {
        UPCOMING, CANCELLED, COMPLETED
    }
}