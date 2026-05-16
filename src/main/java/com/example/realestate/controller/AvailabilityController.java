package com.example.realestate.controller;

import com.example.realestate.models.Availability;
import com.example.realestate.service.AvailabilityService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/availability")
@CrossOrigin("*")
public class AvailabilityController {

    private final AvailabilityService service;

    public AvailabilityController(
            AvailabilityService service
    ) {
        this.service = service;
    }

    @ResponseBody
    @PostMapping
    public Availability addAvailability(
            @RequestBody Availability availability
    ) {
        return service.addAvailability(availability);
    }

    @ResponseBody
    @GetMapping("/agent/{agentId}")
    public List<Availability> getAvailability(
            @PathVariable String agentId
    ) {
        return service.getByAgent(agentId);
    }

    @GetMapping("/page")
    public String page() {
        return "availability";
    }

    @GetMapping("/listPage")
    public String listPage() {
        return "availability-list";
    }


    @ResponseBody
    @DeleteMapping("/{id}")
    public String deleteAvailability(
            @PathVariable Long id
    ) {

        service.deleteAvailability(id);

        return "Availability Deleted";
    }

}
