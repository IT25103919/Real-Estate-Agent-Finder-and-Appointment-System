package com.example.realestate.controller;

import com.example.realestate.models.Appointment;
import com.example.realestate.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
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


    @GetMapping("/listPage")
    public String listPage() {return "appointments-list";
    }

    // Create Appointment
    @ResponseBody
    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return appointmentService.createAppointment(appointment);
    }

    // Get All Appointments
    @ResponseBody
    @GetMapping
    public List<Appointment> getAllAppointments() {

        return appointmentService.getAllAppointments();
    }

    // Get Appointments By Client
    @ResponseBody
    @GetMapping("/client/{clientId}")
    public List<Appointment> getByClient(@PathVariable String clientId) {

        return appointmentService.getAppointmentsByClientId(clientId);
    }

    // Get Appointments By Agent
    @ResponseBody
    @GetMapping("/agent/{agentId}")
    public List<Appointment> getByAgent(@PathVariable String agentId) {

        return appointmentService.getAppointmentsByAgentId(agentId);
    }

    // Approve Appointment
    @ResponseBody
    @PutMapping("/{id}/approve")
    public Appointment approveAppointment(@PathVariable Long id) {

        return appointmentService.approveAppointment(id);
    }

    // Complete Appointment
    @ResponseBody
    @PutMapping("/{id}/complete")
    public Appointment completeAppointment(@PathVariable Long id) {

        return appointmentService.completeAppointment(id);
    }

    // Reject Appointment
    @ResponseBody
    @PutMapping("/{id}/reject")
    public Appointment rejectAppointment(@PathVariable Long id) {

        return appointmentService.rejectAppointment(id);
    }

    // Delete Appointment
    @ResponseBody
    @DeleteMapping("/{id}")
    public String deleteAppointment(@PathVariable Long id) {

        appointmentService.deleteAppointment(id);

        return "Appointment Deleted Successfully";
    }

}
