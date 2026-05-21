package com.example.realestate.dto;

public class ComplaintRequest {
    private Long clientId;
    private Long agentId;
    private String description;

    // --- Getters and Setters ---
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public Long getAgentId() { return agentId; }
    public void setAgentId(Long agentId) { this.agentId = agentId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}