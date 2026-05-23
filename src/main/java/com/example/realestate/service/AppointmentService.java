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

    // Update Appointment (editable fields only — status and IDs are preserved)
    public Appointment updateAppointment(Long id, Appointment updates) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        existing.setDate(updates.getDate());
        existing.setTime(updates.getTime());
        existing.setMeetingType(updates.getMeetingType());
        existing.setMeetingLink(updates.getMeetingLink());
        existing.setNotes(updates.getNotes());
        // status, clientId, agentId intentionally NOT updated here

        return appointmentRepository.save(existing);
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