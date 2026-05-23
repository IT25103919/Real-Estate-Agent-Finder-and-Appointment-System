package com.example.realestate.controller;

import com.example.realestate.models.Appointment;
import com.example.realestate.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
@CrossOrigin("*")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping({"/bookingPage", "/appointments/bookingPage"})
    public String bookingPage() {
        return "forward:/booking.html";
    }

    @GetMapping({"/appointmentsPage", "/appointments/appointmentsPage"})
    public String appointmentsPage() {
        return "forward:/appointments.html";
    }

    @GetMapping({"/listPage", "/appointments/listPage"})
    public String listPage() {
        return "forward:/appointments-list.html";
    }

    // Create Appointment
    @ResponseBody
    @PostMapping("/appointments")
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return appointmentService.createAppointment(appointment);
    }

    // Get All Appointments
    @ResponseBody
    @GetMapping("/appointments")
    public List<Appointment> getAllAppointments() {

        return appointmentService.getAllAppointments();
    }

    // Get enriched appointments with resolved names
    @ResponseBody
    @GetMapping("/appointments/enriched")
    public List<com.example.realestate.dto.AppointmentDto> getAllAppointmentsEnriched() {
        return appointmentService.getAllAppointmentsEnriched();
    }

    // Get Appointments By Client
    @ResponseBody
    @GetMapping("/appointments/client/{clientId}")
    public List<Appointment> getByClient(@PathVariable String clientId) {

        return appointmentService.getAppointmentsByClientId(clientId);
    }

    // Get enriched appointments for a client (resolves names)
    @ResponseBody
    @GetMapping("/appointments/enriched/client/{clientId}")
    public List<com.example.realestate.dto.AppointmentDto> getEnrichedByClient(@PathVariable String clientId) {
        return appointmentService.getAppointmentsEnrichedByClientId(clientId);
    }

    // Get Appointments By Agent
    @ResponseBody
    @GetMapping("/appointments/agent/{agentId}")
    public List<Appointment> getByAgent(@PathVariable String agentId) {

        return appointmentService.getAppointmentsByAgentId(agentId);
    }

    // Get enriched appointments for an agent (resolves names)
    @ResponseBody
    @GetMapping("/appointments/enriched/agent/{agentId}")
    public List<com.example.realestate.dto.AppointmentDto> getEnrichedByAgent(@PathVariable String agentId) {
        return appointmentService.getAppointmentsEnrichedByAgentId(agentId);
    }

    // Approve Appointment
    @ResponseBody
    @PutMapping("/appointments/{id}/approve")
    public Appointment approveAppointment(@PathVariable Long id) {

        return appointmentService.approveAppointment(id);
    }

    // Complete Appointment
    @ResponseBody
    @PutMapping("/appointments/{id}/complete")
    public Appointment completeAppointment(@PathVariable Long id) {

        return appointmentService.completeAppointment(id);
    }

    // Reject Appointment
    @ResponseBody
    @PutMapping("/appointments/{id}/reject")
    public Appointment rejectAppointment(@PathVariable Long id) {

        return appointmentService.rejectAppointment(id);
    }

    // Delete Appointment
    @ResponseBody
    @DeleteMapping("/appointments/{id}")
    public String deleteAppointment(@PathVariable Long id) {

        appointmentService.deleteAppointment(id);

        return "Appointment Deleted Successfully";
    }

    // Exception Handler for double booking and other validation exceptions
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}