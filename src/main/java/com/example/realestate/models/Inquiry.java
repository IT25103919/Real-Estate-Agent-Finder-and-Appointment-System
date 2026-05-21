package com.example.realestate.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inquiries")
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @Column(name = "property_id", nullable = false)
    private Long propertyId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "is_replied")
    private boolean isReplied = false;

    @Column(name = "sent_at")
    private LocalDateTime sentAt = LocalDateTime.now();

    @Column(name = "replied_at")
    private LocalDateTime repliedAt;

    public Inquiry() {
    }

    public Inquiry(Long id, Client client, Agent agent, Long propertyId, String message, boolean isReplied, LocalDateTime sentAt, LocalDateTime repliedAt) {
        this.id = id;
        this.client = client;
        this.agent = agent;
        this.propertyId = propertyId;
        this.message = message;
        this.isReplied = isReplied;
        this.sentAt = sentAt;
        this.repliedAt = repliedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Agent getAgent() { return agent; }
    public void setAgent(Agent agent) { this.agent = agent; }

    public Long getPropertyId() { return propertyId; }
    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isReplied() { return isReplied; }
    public void setReplied(boolean isReplied) { this.isReplied = isReplied; }

    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

    public LocalDateTime getRepliedAt() { return repliedAt; }
    public void setRepliedAt(LocalDateTime repliedAt) { this.repliedAt = repliedAt; }
}