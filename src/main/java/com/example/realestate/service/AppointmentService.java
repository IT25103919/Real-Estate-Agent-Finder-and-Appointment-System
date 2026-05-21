package com.example.realestate.service;

import com.example.realestate.models.Appointment;
import com.example.realestate.repositories.AppointmentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

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

        return appointmentRepository.save(appointment);
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

    // Approve Appointment
    public Appointment approveAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus("Approved");

        return appointmentRepository.save(appointment);
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

        return appointmentRepository.save(appointment);
    }

}
