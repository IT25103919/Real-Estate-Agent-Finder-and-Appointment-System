package com.example.realestate.service;

import com.example.realestate.models.Appointment;
import com.example.realestate.models.Notification;

import com.example.realestate.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private NotificationService notificationService;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment scheduleAppointment(Appointment appointment) {
        Appointment saved = appointmentRepository.save(appointment);

        // Error Fix: .getClientId() වෙනුවට .getClient().getId() භාවිතය
        notificationService.createNotification(
                appointment.getClient().getId(),
                Notification.NotificationType.MEETING_REMINDER,
                "Meeting Scheduled",
                "You have a new meeting scheduled on " + appointment.getScheduledAt(),
                saved.getId()
        );

        // Error Fix: .getAgentId() වෙනුවට .getAgent().getId() භාවිතය
        notificationService.createNotification(
                appointment.getAgent().getId(),
                Notification.NotificationType.MEETING_REMINDER,
                "New Appointment Request",
                "A client has requested a meeting on " + appointment.getScheduledAt(),
                saved.getId()
        );

        return saved;
    }

    public Appointment cancelAppointment(Long id) {
        Appointment app = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        app.setStatus(Appointment.AppointmentStatus.CANCELLED);
        Appointment saved = appointmentRepository.save(app);

        // Error Fix: app.getClientId() වෙනුවට app.getClient().getId() භාවිතය
        notificationService.createNotification(
                app.getClient().getId(),
                Notification.NotificationType.APPOINTMENT_CANCELLED,
                "Meeting Cancelled",
                "Your appointment scheduled for " + app.getScheduledAt() + " has been cancelled.",
                id
        );

        return saved;
    }
}