package com.example.realestate.service;

import com.example.realestate.models.Appointment;
import com.example.realestate.models.Notification;
import com.example.realestate.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private com.example.realestate.repositories.AgentRepository agentRepository;

    @Autowired
    private com.example.realestate.repositories.ClientRepository clientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // Create Appointment
    public Appointment createAppointment(Appointment appointment) {

        // Prevent double booking
        boolean alreadyBooked =
                appointmentRepository.existsByAgentIdAndDateAndTime(appointment.getAgentId(), appointment.getDate(), appointment.getTime());

        if (alreadyBooked) {
            throw new RuntimeException("Agent already booked at this time");
        }

        appointment.setStatus("Pending");

        Appointment saved = appointmentRepository.save(appointment);

        // Publish notification for the Agent
        try {
            Long agentId = Long.parseLong(saved.getAgentId());
            notificationService.createNotification(
                    agentId,
                    Notification.NotificationType.MEETING_REMINDER,
                    "New Appointment Request",
                    "A client has requested a virtual appointment on " + saved.getDate() + " at " + saved.getTime() + ".",
                    saved.getId()
            );
        } catch (NumberFormatException e) {
            System.err.println("Error parsing Agent ID for notification: " + saved.getAgentId());
        }

        // Publish notification for the Client
        try {
            Long clientId = Long.parseLong(saved.getClientId());
            notificationService.createNotification(
                    clientId,
                    Notification.NotificationType.GENERAL,
                    "Appointment Request Submitted",
                    "Your virtual appointment request for " + saved.getDate() + " at " + saved.getTime() + " has been successfully submitted.",
                    saved.getId()
            );
        } catch (NumberFormatException e) {
            System.err.println("Error parsing Client ID for notification: " + saved.getClientId());
        }

        return saved;
    }

    // Get All Appointments
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // Get All Appointments enriched with client/agent names
    public List<com.example.realestate.dto.AppointmentDto> getAllAppointmentsEnriched() {
        List<Appointment> list = appointmentRepository.findAll();
        List<com.example.realestate.dto.AppointmentDto> out = new java.util.ArrayList<>();

        for (Appointment a : list) {
            com.example.realestate.dto.AppointmentDto dto = new com.example.realestate.dto.AppointmentDto();
            dto.id = a.getId();
            dto.clientId = a.getClientId();
            dto.agentId = a.getAgentId();
            dto.date = a.getDate();
            dto.time = a.getTime();
            dto.status = a.getStatus();
            dto.notes = a.getNotes();

            // Resolve client name
            String clientName = null;
            String cid = a.getClientId();
            if (cid != null) {
                try {
                    Long lid = Long.parseLong(cid.trim());
                    clientName = clientRepository.findById(lid).map(com.example.realestate.models.Client::getFullName).orElse(null);
                } catch (NumberFormatException ignored) {
                    // try extract digits
                    java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d+)").matcher(cid);
                    if (m.find()) {
                        try {
                            Long lid2 = Long.parseLong(m.group(1));
                            clientName = clientRepository.findById(lid2).map(com.example.realestate.models.Client::getFullName).orElse(null);
                        } catch (NumberFormatException e) {}
                    }
                }
            }
            dto.clientName = clientName != null ? clientName : (cid != null ? cid : "Unknown");

            // Resolve agent name
            String agentName = null;
            String aid = a.getAgentId();
            if (aid != null) {
                try {
                    Long lid = Long.parseLong(aid.trim());
                    agentName = agentRepository.findById(lid).map(com.example.realestate.models.Agent::getFullName).orElse(null);
                } catch (NumberFormatException ignored) {
                    java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d+)").matcher(aid);
                    if (m.find()) {
                        try {
                            Long lid2 = Long.parseLong(m.group(1));
                            agentName = agentRepository.findById(lid2).map(com.example.realestate.models.Agent::getFullName).orElse(null);
                        } catch (NumberFormatException e) {}
                    }
                }
            }
            dto.agentName = agentName != null ? agentName : (aid != null ? aid : "Unknown");

            out.add(dto);
        }

        return out;
    }

    // Get enriched appointments for a specific clientId
    public List<com.example.realestate.dto.AppointmentDto> getAppointmentsEnrichedByClientId(String clientId) {
        List<Appointment> list = appointmentRepository.findByClientId(clientId);
        List<com.example.realestate.dto.AppointmentDto> out = new java.util.ArrayList<>();

        for (Appointment a : list) {
            com.example.realestate.dto.AppointmentDto dto = new com.example.realestate.dto.AppointmentDto();
            dto.id = a.getId();
            dto.clientId = a.getClientId();
            dto.agentId = a.getAgentId();
            dto.date = a.getDate();
            dto.time = a.getTime();
            dto.status = a.getStatus();
            dto.notes = a.getNotes();

            // Resolve client name (reuse logic)
            String clientName = null;
            String cid = a.getClientId();
            if (cid != null) {
                try {
                    Long lid = Long.parseLong(cid.trim());
                    clientName = clientRepository.findById(lid).map(com.example.realestate.models.Client::getFullName).orElse(null);
                } catch (NumberFormatException ignored) {
                    java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d+)").matcher(cid);
                    if (m.find()) {
                        try {
                            Long lid2 = Long.parseLong(m.group(1));
                            clientName = clientRepository.findById(lid2).map(com.example.realestate.models.Client::getFullName).orElse(null);
                        } catch (NumberFormatException e) {}
                    }
                }
            }
            dto.clientName = clientName != null ? clientName : (cid != null ? cid : "Unknown");

            // Resolve agent name
            String agentName = null;
            String aid = a.getAgentId();
            if (aid != null) {
                try {
                    Long lid = Long.parseLong(aid.trim());
                    agentName = agentRepository.findById(lid).map(com.example.realestate.models.Agent::getFullName).orElse(null);
                } catch (NumberFormatException ignored) {
                    java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d+)").matcher(aid);
                    if (m.find()) {
                        try {
                            Long lid2 = Long.parseLong(m.group(1));
                            agentName = agentRepository.findById(lid2).map(com.example.realestate.models.Agent::getFullName).orElse(null);
                        } catch (NumberFormatException e) {}
                    }
                }
            }
            dto.agentName = agentName != null ? agentName : (aid != null ? aid : "Unknown");

            out.add(dto);
        }

        return out;
    }

    // Get enriched appointments for a specific agentId
    public List<com.example.realestate.dto.AppointmentDto> getAppointmentsEnrichedByAgentId(String agentId) {
        List<Appointment> list = appointmentRepository.findByAgentId(agentId);
        List<com.example.realestate.dto.AppointmentDto> out = new java.util.ArrayList<>();

        for (Appointment a : list) {
            com.example.realestate.dto.AppointmentDto dto = new com.example.realestate.dto.AppointmentDto();
            dto.id = a.getId();
            dto.clientId = a.getClientId();
            dto.agentId = a.getAgentId();
            dto.date = a.getDate();
            dto.time = a.getTime();
            dto.status = a.getStatus();
            dto.notes = a.getNotes();

            // Resolve client name
            String clientName = null;
            String cid = a.getClientId();
            if (cid != null) {
                try {
                    Long lid = Long.parseLong(cid.trim());
                    clientName = clientRepository.findById(lid).map(com.example.realestate.models.Client::getFullName).orElse(null);
                } catch (NumberFormatException ignored) {
                    java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d+)").matcher(cid);
                    if (m.find()) {
                        try {
                            Long lid2 = Long.parseLong(m.group(1));
                            clientName = clientRepository.findById(lid2).map(com.example.realestate.models.Client::getFullName).orElse(null);
                        } catch (NumberFormatException e) {}
                    }
                }
            }
            dto.clientName = clientName != null ? clientName : (cid != null ? cid : "Unknown");

            // Resolve agent name
            String agentName = null;
            String aid = a.getAgentId();
            if (aid != null) {
                try {
                    Long lid = Long.parseLong(aid.trim());
                    agentName = agentRepository.findById(lid).map(com.example.realestate.models.Agent::getFullName).orElse(null);
                } catch (NumberFormatException ignored) {
                    java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d+)").matcher(aid);
                    if (m.find()) {
                        try {
                            Long lid2 = Long.parseLong(m.group(1));
                            agentName = agentRepository.findById(lid2).map(com.example.realestate.models.Agent::getFullName).orElse(null);
                        } catch (NumberFormatException e) {}
                    }
                }
            }
            dto.agentName = agentName != null ? agentName : (aid != null ? aid : "Unknown");

            out.add(dto);
        }

        return out;
    }

    // Get Client Appointments
    public List<Appointment> getAppointmentsByClientId(
            String clientId
    ) {
        return appointmentRepository.findByClientId(clientId);
    }

    // Get Agent Appointments
    public List<Appointment> getAppointmentsByAgentId(
            String agentId
    ) {
        return appointmentRepository.findByAgentId(agentId);
    }

    // Delete Appointment
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    // Approve Appointment
    public Appointment approveAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus("Approved");

        Appointment saved = appointmentRepository.save(appointment);

        // Notify client that meeting is approved
        try {
            Long clientId = Long.parseLong(saved.getClientId());
            notificationService.createNotification(
                    clientId,
                    Notification.NotificationType.MEETING_REMINDER,
                    "Appointment Approved",
                    "Your virtual appointment scheduled for " + saved.getDate() + " at " + saved.getTime() + " has been approved by the agent.",
                    saved.getId()
            );
        } catch (NumberFormatException e) {
            System.err.println("Error parsing Client ID for notification: " + saved.getClientId());
        }

        return saved;
    }

    // Complete Appointment
    public Appointment completeAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus("Completed");

        return appointmentRepository.save(appointment);
    }


    public Appointment rejectAppointment(Long id){

        Appointment appointment =
                appointmentRepository.findById(id).orElseThrow();

        appointment.setStatus("Rejected");

        Appointment saved = appointmentRepository.save(appointment);

        // Notify client that meeting is rejected
        try {
            Long clientId = Long.parseLong(saved.getClientId());
            notificationService.createNotification(
                    clientId,
                    Notification.NotificationType.APPOINTMENT_CANCELLED,
                    "Appointment Declined",
                    "Your virtual appointment request for " + saved.getDate() + " at " + saved.getTime() + " was declined by the agent.",
                    saved.getId()
            );
        } catch (NumberFormatException e) {
            System.err.println("Error parsing Client ID for notification: " + saved.getClientId());
        }

        return saved;
    }

}