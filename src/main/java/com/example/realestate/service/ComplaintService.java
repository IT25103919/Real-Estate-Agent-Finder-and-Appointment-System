package com.example.realestate.service;

import com.example.realestate.dto.ComplaintRequest;
import com.example.realestate.models.Agent;
import com.example.realestate.models.Client;
import com.example.realestate.models.Complaint;
import com.example.realestate.repositories.AgentRepository;
import com.example.realestate.repositories.ClientRepository;
import com.example.realestate.repositories.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AgentRepository agentRepository;

    public String fileComplaint(ComplaintRequest request) {


        Optional<Client> clientOpt = clientRepository.findById(request.getClientId());
        if (clientOpt.isEmpty()) {
            return "CLIENT_NOT_FOUND";
        }


        Optional<Agent> agentOpt = agentRepository.findById(request.getAgentId());
        if (agentOpt.isEmpty()) {
            return "AGENT_NOT_FOUND";
        }

        // Create and save the complaint
        Complaint complaint = new Complaint();
        complaint.setClient(clientOpt.get());
        complaint.setAgent(agentOpt.get());
        complaint.setDescription(request.getDescription());
        // status defaults to PENDING automatically from Complaint.java

        complaintRepository.save(complaint);
        return "SUCCESS";
    }
}