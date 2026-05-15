package com.example.realestate.controller;

import com.example.realestate.models.Appointment;
import com.example.realestate.service.AppointmentService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@ResponseBody
@RestController
@RequestMapping("/appointments")
@CrossOrigin("*")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    @GetMapping("/bookingPage")
    public String bookingPage() {
        return "booking";
    }

    @GetMapping("/appointmentsPage")
    public String appointmentsPage() {
        return "appointments";
    }

    // Create Appointment
    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return appointmentService.createAppointment(appointment);
    }

    // Get All Appointments
    @GetMapping
    public List<Appointment> getAllAppointments() {

        return appointmentService.getAllAppointments();
    }

    // Get Appointments By Client
    @GetMapping("/client/{clientId}")
    public List<Appointment> getByClient(@PathVariable String clientId) {

        return appointmentService.getAppointmentsByClientId(clientId);
    }

    // Get Appointments By Agent
    @GetMapping("/agent/{agentId}")
    public List<Appointment> getByAgent(@PathVariable String agentId) {

        return appointmentService.getAppointmentsByAgentId(agentId);
    }

    // Approve Appointment
    @PutMapping("/{id}/approve")
    public Appointment approveAppointment(@PathVariable Long id) {

        return appointmentService.approveAppointment(id);
    }

    // Complete Appointment
    @PutMapping("/{id}/complete")
    public Appointment completeAppointment(@PathVariable Long id) {

        return appointmentService.completeAppointment(id);
    }

    // Delete Appointment
    @DeleteMapping("/{id}")
    public String deleteAppointment(@PathVariable Long id) {

        appointmentService.deleteAppointment(id);

        return "Appointment Deleted Successfully";
    }

}
